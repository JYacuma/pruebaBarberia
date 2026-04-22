package com.barberia.service;

import com.barberia.dto.ResenaDTO;
import java.util.List;

public interface ResenaService {
    List<ResenaDTO> listarPorBarbero(Long barberoId);
    ResenaDTO buscarPorId(Long id);
    ResenaDTO crear(ResenaDTO dto);
}
