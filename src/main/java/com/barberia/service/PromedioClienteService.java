package com.barberia.service;

import com.barberia.dto.PromedioClienteDTO;

public interface PromedioClienteService {
    PromedioClienteDTO buscarPorUsuarioYBarbero(Long usuarioId, Long barberoId);
    PromedioClienteDTO actualizarPromedio(Long usuarioId, Long barberoId, long duracionMinutosReal);
}
