package com.barberia.controller;

import com.barberia.dto.ResenaDTO;
import com.barberia.service.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    /* -- GET /api/resenas/barbero/{barberoId} -- */
    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<ResenaDTO>> listarPorBarbero(@PathVariable Long barberoId) {
        return ResponseEntity.ok(resenaService.listarPorBarbero(barberoId));
    }

    /* -- GET /api/resenas/{id} -- */
    @GetMapping("/{id}")
    public ResponseEntity<ResenaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.buscarPorId(id));
    }

    /* -- POST /api/resenas -- */
    @PostMapping
    public ResponseEntity<ResenaDTO> crear(@RequestBody ResenaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.crear(dto));
    }
}
