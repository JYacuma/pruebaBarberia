package com.barberia.service;

import com.barberia.dto.ServicioDTO;
import java.util.List;

public interface ServicioService {
    List<ServicioDTO> listarTodos();
    ServicioDTO buscarPorId(Long id);
    ServicioDTO crear(ServicioDTO dto);
    ServicioDTO actualizar(Long id, ServicioDTO dto);
    void eliminar(Long id);
}
