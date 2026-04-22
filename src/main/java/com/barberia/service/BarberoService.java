package com.barberia.service;

import com.barberia.dto.BarberoDTO;
import java.util.List;

public interface BarberoService {
    List<BarberoDTO> listarTodos();
    List<BarberoDTO> listarActivos();
    BarberoDTO buscarPorId(Long id);
    BarberoDTO crear(BarberoDTO dto);
    BarberoDTO actualizar(Long id, BarberoDTO dto);
    void eliminar(Long id);
}
