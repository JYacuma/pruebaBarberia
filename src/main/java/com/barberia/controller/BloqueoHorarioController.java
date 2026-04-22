package com.barberia.controller;

import com.barberia.dto.BloqueoHorarioDTO;
import com.barberia.service.BloqueoHorarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bloqueos")
public class BloqueoHorarioController {

    private final BloqueoHorarioService bloqueoService;

    public BloqueoHorarioController(BloqueoHorarioService bloqueoService) {
        this.bloqueoService = bloqueoService;
    }

    /* -- GET /api/bloqueos/barbero/{barberoId} -- */
    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<BloqueoHorarioDTO>> listarPorBarbero(@PathVariable Long barberoId) {
        return ResponseEntity.ok(bloqueoService.listarPorBarbero(barberoId));
    }

    /* -- GET /api/bloqueos/{id} -- */
    @GetMapping("/{id}")
    public ResponseEntity<BloqueoHorarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(bloqueoService.buscarPorId(id));
    }

    /* -- POST /api/bloqueos -- */
    @PostMapping
    public ResponseEntity<BloqueoHorarioDTO> crear(@RequestBody BloqueoHorarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bloqueoService.crear(dto));
    }

    /* -- DELETE /api/bloqueos/{id} -- */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        bloqueoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
