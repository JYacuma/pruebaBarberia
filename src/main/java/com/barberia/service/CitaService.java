package com.barberia.service;

import com.barberia.dto.CitaDTO;
import java.time.LocalDate;
import java.util.List;

public interface CitaService {
    List<CitaDTO> listarTodas();
    List<CitaDTO> listarPorUsuario(Long usuarioId);
    List<CitaDTO> listarPorBarberoYFecha(Long barberoId, LocalDate fecha);
    CitaDTO buscarPorId(Long id);
    CitaDTO crear(CitaDTO dto);
    CitaDTO cancelar(Long id);
    CitaDTO iniciarCita(Long id);
    CitaDTO finalizarCita(Long id);
    CitaDTO marcarNoPresento(Long id);
}
