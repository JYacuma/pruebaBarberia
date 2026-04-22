package com.barberia.service;

import com.barberia.dto.NotificacionDTO;
import java.util.List;

public interface NotificacionService {
    List<NotificacionDTO> listarPorCita(Long citaId);
    NotificacionDTO enviarNotificacion(Long citaId, String tipo);
}
