package com.barberia.controller;

import com.barberia.dto.CitaDTO;
import com.barberia.service.CitaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    /* -- GET /api/citas -- */
    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarTodas() {
        return ResponseEntity.ok(citaService.listarTodas());
    }

    /* -- GET /api/citas/usuario/{usuarioId} -- */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CitaDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(citaService.listarPorUsuario(usuarioId));
    }

    /* -- GET /api/citas/barbero/{barberoId}?fecha=2026-05-01 -- */
    @GetMapping("/barbero/{barberoId}")
    public ResponseEntity<List<CitaDTO>> listarPorBarberoYFecha(
            @PathVariable Long barberoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.listarPorBarberoYFecha(barberoId, fecha));
    }

    /* -- GET /api/citas/{id} -- */
    @GetMapping("/{id}")
    public ResponseEntity<CitaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.buscarPorId(id));
    }

    /* -- POST /api/citas -- */
    @PostMapping
    public ResponseEntity<CitaDTO> crear(@RequestBody CitaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.crear(dto));
    }

    /* -- PATCH /api/citas/{id}/cancelar -- */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<CitaDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cancelar(id));
    }

    /* -- PATCH /api/citas/{id}/iniciar -- */
    @PatchMapping("/{id}/iniciar")
    public ResponseEntity<CitaDTO> iniciar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.iniciarCita(id));
    }

    /* -- PATCH /api/citas/{id}/finalizar -- */
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<CitaDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.finalizarCita(id));
    }

    /* -- PATCH /api/citas/{id}/no-presento -- */
    @PatchMapping("/{id}/no-presento")
    public ResponseEntity<CitaDTO> noPresento(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.marcarNoPresento(id));
    }
}
