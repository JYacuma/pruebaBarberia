package com.barberia.controller;

import com.barberia.dto.NotificacionDTO;
import com.barberia.service.NotificacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /* -- GET /api/notificaciones/cita/{citaId} -- */
    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<NotificacionDTO>> listarPorCita(@PathVariable Long citaId) {
        return ResponseEntity.ok(notificacionService.listarPorCita(citaId));
    }

    /* -- POST /api/notificaciones/enviar/{citaId}?tipo=CITA_AGENDADA -- */
    @PostMapping("/enviar/{citaId}")
    public ResponseEntity<NotificacionDTO> enviar(
            @PathVariable Long citaId,
            @RequestParam String tipo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.enviarNotificacion(citaId, tipo));
    }
}
