package com.barberia.controller;

import com.barberia.dto.HorarioBarberoDTO;
import com.barberia.service.HorarioBarberoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioBarberoController {

    private final HorarioBarberoService horarioService;

    public HorarioBarberoController(HorarioBarberoService horarioService) {
        this.horarioService = horarioService;
    }

    /* -- GET /api/horarios/barbero/{barberoId} -- */
    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<HorarioBarberoDTO>> listarPorBarbero(@PathVariable Long barberoId) {
        return ResponseEntity.ok(horarioService.listarPorBarbero(barberoId));
    }

    /* -- GET /api/horarios/{id} -- */
    @GetMapping("/{id}")
    public ResponseEntity<HorarioBarberoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(horarioService.buscarPorId(id));
    }

    /* -- POST /api/horarios -- */
    @PostMapping
    public ResponseEntity<HorarioBarberoDTO> crear(@RequestBody HorarioBarberoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioService.crear(dto));
    }

    /* -- PUT /api/horarios/{id} -- */
    @PutMapping("/{id}")
    public ResponseEntity<HorarioBarberoDTO> actualizar(@PathVariable Long id,
                                                         @RequestBody HorarioBarberoDTO dto) {
        return ResponseEntity.ok(horarioService.actualizar(id, dto));
    }

    /* -- DELETE /api/horarios/{id} -- */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        horarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
