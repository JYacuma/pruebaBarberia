package com.barberia.serviceIMP;

import com.barberia.dto.NotificacionDTO;
import com.barberia.enums.TipoNotificacionEnum;
import com.barberia.model.Cita;
import com.barberia.model.Notificacion;
import com.barberia.repository.CitaRepository;
import com.barberia.repository.NotificacionRepository;
import com.barberia.service.NotificacionService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionServiceIMP implements NotificacionService {

    private final NotificacionRepository notificacionRepository;
    private final CitaRepository citaRepository;

    public NotificacionServiceIMP(NotificacionRepository notificacionRepository,
                                   CitaRepository citaRepository) {
        this.notificacionRepository = notificacionRepository;
        this.citaRepository = citaRepository;
    }

    /* -- listar notificaciones de una cita -- */
    @Override
    public List<NotificacionDTO> listarPorCita(Long citaId) {
        return notificacionRepository.findByCitaId(citaId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- simular envio de notificacion whatsapp (en produccion se integra twilio) -- */
    @Override
    public NotificacionDTO enviarNotificacion(Long citaId, String tipo) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + citaId));

        TipoNotificacionEnum tipoEnum = TipoNotificacionEnum.valueOf(tipo.toUpperCase());

        /* -- construir mensaje segun tipo -- */
        String mensaje = tipoEnum == TipoNotificacionEnum.CITA_AGENDADA
                ? "Hola " + cita.getUsuario().getNombre() + ", tu cita fue confirmada para el "
                  + cita.getFecha() + " a las " + cita.getHoraInicio() + " con " + cita.getBarbero().getNombre()
                : "Hola " + cita.getUsuario().getNombre() + ", tu cita del "
                  + cita.getFecha() + " a las " + cita.getHoraInicio() + " fue cancelada.";

        Notificacion n = new Notificacion();
        n.setCita(cita);
        n.setTipo(tipoEnum);
        n.setMensaje(mensaje);
        /* -- en prototipo se marca enviado=true simulando la llamada a twilio -- */
        n.setEnviado(true);

        System.out.println("[NOTIFICACION WHATSAPP SIMULADA] -> " + mensaje);

        return toDTO(notificacionRepository.save(n));
    }

    /* -- convertir entidad a dto -- */
    private NotificacionDTO toDTO(Notificacion n) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(n.getId());
        dto.setCitaId(n.getCita().getId());
        dto.setTipo(n.getTipo());
        dto.setMensaje(n.getMensaje());
        dto.setEnviado(n.isEnviado());
        return dto;
    }
}
