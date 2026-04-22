package com.barberia.controller;

import com.barberia.dto.PromedioClienteDTO;
import com.barberia.service.PromedioClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promedios")
public class PromedioClienteController {

    private final PromedioClienteService promedioService;

    public PromedioClienteController(PromedioClienteService promedioService) {
        this.promedioService = promedioService;
    }

    /* -- GET /api/promedios?usuarioId=1&barberoId=2 -- */
    @GetMapping
    public ResponseEntity<PromedioClienteDTO> buscar(
            @RequestParam Long usuarioId,
            @RequestParam Long barberoId) {
        return ResponseEntity.ok(promedioService.buscarPorUsuarioYBarbero(usuarioId, barberoId));
    }
}
