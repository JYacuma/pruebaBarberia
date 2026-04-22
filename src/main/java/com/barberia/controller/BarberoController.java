package com.barberia.controller;

import com.barberia.dto.BarberoDTO;
import com.barberia.service.BarberoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/barberos")
public class BarberoController {

    private final BarberoService barberoService;

    public BarberoController(BarberoService barberoService) {
        this.barberoService = barberoService;
    }

    /* -- GET /api/barberos -- */
    @GetMapping
    public ResponseEntity<List<BarberoDTO>> listarTodos() {
        return ResponseEntity.ok(barberoService.listarTodos());
    }

    /* -- GET /api/barberos/activos -- */
    @GetMapping("/activos")
    public ResponseEntity<List<BarberoDTO>> listarActivos() {
        return ResponseEntity.ok(barberoService.listarActivos());
    }

    /* -- GET /api/barberos/{id} -- */
    @GetMapping("/{id}")
    public ResponseEntity<BarberoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(barberoService.buscarPorId(id));
    }

    /* -- POST /api/barberos -- */
    @PostMapping
    public ResponseEntity<BarberoDTO> crear(@RequestBody BarberoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(barberoService.crear(dto));
    }

    /* -- PUT /api/barberos/{id} -- */
    @PutMapping("/{id}")
    public ResponseEntity<BarberoDTO> actualizar(@PathVariable Long id, @RequestBody BarberoDTO dto) {
        return ResponseEntity.ok(barberoService.actualizar(id, dto));
    }

    /* -- DELETE /api/barberos/{id} -- */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        barberoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
