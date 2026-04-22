package com.barberia.serviceIMP;

import com.barberia.dto.CitaDTO;
import com.barberia.enums.EstadoCitaEnum;
import com.barberia.model.*;
import com.barberia.repository.*;
import com.barberia.service.CitaService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CitaServiceIMP implements CitaService {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BarberoRepository barberoRepository;
    private final ServicioRepository servicioRepository;
    private final PromedioClienteRepository promedioClienteRepository;

    public CitaServiceIMP(CitaRepository citaRepository,
                           UsuarioRepository usuarioRepository,
                           BarberoRepository barberoRepository,
                           ServicioRepository servicioRepository,
                           PromedioClienteRepository promedioClienteRepository) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.barberoRepository = barberoRepository;
        this.servicioRepository = servicioRepository;
        this.promedioClienteRepository = promedioClienteRepository;
    }

    /* -- listar todas las citas -- */
    @Override
    public List<CitaDTO> listarTodas() {
        return citaRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- listar citas de un usuario cliente -- */
    @Override
    public List<CitaDTO> listarPorUsuario(Long usuarioId) {
        return citaRepository.findByUsuarioId(usuarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- listar citas de un barbero en una fecha -- */
    @Override
    public List<CitaDTO> listarPorBarberoYFecha(Long barberoId, LocalDate fecha) {
        return citaRepository.findByBarberoIdAndFecha(barberoId, fecha)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- buscar cita por id -- */
    @Override
    public CitaDTO buscarPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        return toDTO(cita);
    }

    /* -- crear nueva cita con logica de bloqueo por promedio -- */
    @Override
    public CitaDTO crear(CitaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        Barbero barbero = barberoRepository.findById(dto.getBarberoId())
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + dto.getBarberoId()));
        Servicio servicio = servicioRepository.findById(dto.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + dto.getServicioId()));

        /* -- calcular hora_fin segun promedio historico del cliente o duracion del servicio -- */
        int minutosEstimados = calcularMinutosEstimados(usuario.getId(), barbero.getId(), servicio.getDuracionMinutos());
        LocalTime horaFin = dto.getHoraInicio().plusMinutes(minutosEstimados);

        /* -- verificar que el horario este disponible -- */
        verificarDisponibilidad(barbero.getId(), dto.getFecha(), dto.getHoraInicio(), horaFin);

        Cita cita = new Cita();
        cita.setUsuario(usuario);
        cita.setBarbero(barbero);
        cita.setServicio(servicio);
        cita.setFecha(dto.getFecha());
        cita.setHoraInicio(dto.getHoraInicio());
        cita.setHoraFin(horaFin);
        cita.setEstado(EstadoCitaEnum.PENDIENTE);

        return toDTO(citaRepository.save(cita));
    }

    /* -- cancelar una cita activa -- */
    @Override
    public CitaDTO cancelar(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        if (cita.getEstado() == EstadoCitaEnum.FINALIZADA) {
            throw new RuntimeException("No se puede cancelar una cita ya finalizada");
        }
        cita.setEstado(EstadoCitaEnum.CANCELADA);
        return toDTO(citaRepository.save(cita));
    }

    /* -- barbero confirma inicio de cita -- */
    @Override
    public CitaDTO iniciarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        if (cita.getEstado() != EstadoCitaEnum.PENDIENTE) {
            throw new RuntimeException("Solo se puede iniciar una cita en estado PENDIENTE");
        }
        cita.setEstado(EstadoCitaEnum.EN_CURSO);
        cita.setHoraInicioReal(LocalDateTime.now());
        return toDTO(citaRepository.save(cita));
    }

    /* -- barbero confirma fin de cita y actualiza promedio del cliente -- */
    @Override
    public CitaDTO finalizarCita(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        if (cita.getEstado() != EstadoCitaEnum.EN_CURSO) {
            throw new RuntimeException("Solo se puede finalizar una cita en estado EN_CURSO");
        }
        cita.setEstado(EstadoCitaEnum.FINALIZADA);
        cita.setHoraFinReal(LocalDateTime.now());

        /* -- calcular duracion real y actualizar promedio -- */
        if (cita.getHoraInicioReal() != null) {
            long duracionReal = ChronoUnit.MINUTES.between(cita.getHoraInicioReal(), cita.getHoraFinReal());
            actualizarPromedio(cita.getUsuario().getId(), cita.getBarbero().getId(), duracionReal);
        }

        return toDTO(citaRepository.save(cita));
    }

    /* -- barbero marca cliente como no presentado -- */
    @Override
    public CitaDTO marcarNoPresento(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
        cita.setEstado(EstadoCitaEnum.NO_PRESENTADO);
        cita.setNoPresento(true);
        return toDTO(citaRepository.save(cita));
    }

    /* -- calcular minutos estimados segun promedio del cliente o default del servicio -- */
    private int calcularMinutosEstimados(Long usuarioId, Long barberoId, int duracionServicio) {
        Optional<PromedioCliente> promedio = promedioClienteRepository
                .findByUsuarioIdAndBarberoId(usuarioId, barberoId);
        if (promedio.isPresent() && promedio.get().getTotalCitas() > 0) {
            return (int) Math.ceil(promedio.get().getDuracionPromedioMinutos());
        }
        /* -- sin historial usa la duracion del servicio (entre 30 y 40 min minimo) -- */
        return Math.max(duracionServicio, 35);
    }

    /* -- verificar que no haya conflicto de horario con otras citas activas -- */
    private void verificarDisponibilidad(Long barberoId, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        List<Cita> citasActivas = citaRepository.findByBarberoIdAndFechaAndEstadoNot(
                barberoId, fecha, EstadoCitaEnum.CANCELADA);
        for (Cita c : citasActivas) {
            if (c.getEstado() == EstadoCitaEnum.NO_PRESENTADO) continue;
            boolean hayConflicto = horaInicio.isBefore(c.getHoraFin()) && horaFin.isAfter(c.getHoraInicio());
            if (hayConflicto) {
                throw new RuntimeException("El barbero ya tiene una cita en ese horario: "
                        + c.getHoraInicio() + " - " + c.getHoraFin());
            }
        }
    }

    /* -- actualizar promedio del cliente despues de finalizar cita -- */
    private void actualizarPromedio(Long usuarioId, Long barberoId, long duracionReal) {
        PromedioCliente promedio = promedioClienteRepository
                .findByUsuarioIdAndBarberoId(usuarioId, barberoId)
                .orElseGet(() -> {
                    PromedioCliente nuevo = new PromedioCliente();
                    nuevo.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow());
                    nuevo.setBarbero(barberoRepository.findById(barberoId).orElseThrow());
                    nuevo.setTotalCitas(0);
                    nuevo.setDuracionPromedioMinutos(35.0);
                    return nuevo;
                });
        /* -- formula de promedio incremental: promedio = ((promedio * total) + nuevo) / (total + 1) -- */
        double nuevoPromedio = ((promedio.getDuracionPromedioMinutos() * promedio.getTotalCitas()) + duracionReal)
                / (promedio.getTotalCitas() + 1);
        promedio.setDuracionPromedioMinutos(nuevoPromedio);
        promedio.setTotalCitas(promedio.getTotalCitas() + 1);
        promedioClienteRepository.save(promedio);
    }

    /* -- convertir entidad a dto -- */
    private CitaDTO toDTO(Cita c) {
        CitaDTO dto = new CitaDTO();
        dto.setId(c.getId());
        dto.setUsuarioId(c.getUsuario().getId());
        dto.setBarberoId(c.getBarbero().getId());
        dto.setServicioId(c.getServicio().getId());
        dto.setFecha(c.getFecha());
        dto.setHoraInicio(c.getHoraInicio());
        dto.setEstado(c.getEstado());
        return dto;
    }
}
