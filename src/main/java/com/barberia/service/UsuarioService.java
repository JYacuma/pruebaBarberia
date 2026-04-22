package com.barberia.service;

import com.barberia.dto.UsuarioDTO;
import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> listarTodos();
    UsuarioDTO buscarPorId(Long id);
    UsuarioDTO crear(UsuarioDTO dto);
    UsuarioDTO actualizar(Long id, UsuarioDTO dto);
    void eliminar(Long id);
}
