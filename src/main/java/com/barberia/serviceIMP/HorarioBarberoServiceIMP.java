package com.barberia.serviceIMP;

import com.barberia.dto.HorarioBarberoDTO;
import com.barberia.model.Barbero;
import com.barberia.model.HorarioBarbero;
import com.barberia.repository.BarberoRepository;
import com.barberia.repository.HorarioBarberoRepository;
import com.barberia.service.HorarioBarberoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioBarberoServiceIMP implements HorarioBarberoService {

    private final HorarioBarberoRepository horarioRepository;
    private final BarberoRepository barberoRepository;

    public HorarioBarberoServiceIMP(HorarioBarberoRepository horarioRepository,
                                     BarberoRepository barberoRepository) {
        this.horarioRepository = horarioRepository;
        this.barberoRepository = barberoRepository;
    }

    /* -- listar horarios de un barbero -- */
    @Override
    public List<HorarioBarberoDTO> listarPorBarbero(Long barberoId) {
        return horarioRepository.findByBarberoId(barberoId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- buscar horario por id -- */
    @Override
    public HorarioBarberoDTO buscarPorId(Long id) {
        HorarioBarbero h = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));
        return toDTO(h);
    }

    /* -- crear horario para un barbero -- */
    @Override
    public HorarioBarberoDTO crear(HorarioBarberoDTO dto) {
        Barbero barbero = barberoRepository.findById(dto.getBarberoId())
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + dto.getBarberoId()));
        HorarioBarbero h = new HorarioBarbero();
        h.setBarbero(barbero);
        h.setDiaSemana(dto.getDiaSemana());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        return toDTO(horarioRepository.save(h));
    }

    /* -- actualizar horario -- */
    @Override
    public HorarioBarberoDTO actualizar(Long id, HorarioBarberoDTO dto) {
        HorarioBarbero h = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));
        h.setDiaSemana(dto.getDiaSemana());
        h.setHoraInicio(dto.getHoraInicio());
        h.setHoraFin(dto.getHoraFin());
        return toDTO(horarioRepository.save(h));
    }

    /* -- eliminar horario -- */
    @Override
    public void eliminar(Long id) {
        horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));
        horarioRepository.deleteById(id);
    }

    /* -- convertir entidad a dto -- */
    private HorarioBarberoDTO toDTO(HorarioBarbero h) {
        HorarioBarberoDTO dto = new HorarioBarberoDTO();
        dto.setId(h.getId());
        dto.setBarberoId(h.getBarbero().getId());
        dto.setDiaSemana(h.getDiaSemana());
        dto.setHoraInicio(h.getHoraInicio());
        dto.setHoraFin(h.getHoraFin());
        return dto;
    }
}
