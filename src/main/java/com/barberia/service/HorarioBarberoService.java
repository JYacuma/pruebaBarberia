package com.barberia.service;

import com.barberia.dto.HorarioBarberoDTO;
import java.util.List;

public interface HorarioBarberoService {
    List<HorarioBarberoDTO> listarPorBarbero(Long barberoId);
    HorarioBarberoDTO buscarPorId(Long id);
    HorarioBarberoDTO crear(HorarioBarberoDTO dto);
    HorarioBarberoDTO actualizar(Long id, HorarioBarberoDTO dto);
    void eliminar(Long id);
}
