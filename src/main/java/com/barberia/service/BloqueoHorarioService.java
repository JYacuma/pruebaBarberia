package com.barberia.service;

import com.barberia.dto.BloqueoHorarioDTO;
import java.util.List;

public interface BloqueoHorarioService {
    List<BloqueoHorarioDTO> listarPorBarbero(Long barberoId);
    BloqueoHorarioDTO buscarPorId(Long id);
    BloqueoHorarioDTO crear(BloqueoHorarioDTO dto);
    void eliminar(Long id);
}
