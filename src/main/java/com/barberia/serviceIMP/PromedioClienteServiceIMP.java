package com.barberia.serviceIMP;

import com.barberia.dto.PromedioClienteDTO;
import com.barberia.model.PromedioCliente;
import com.barberia.repository.BarberoRepository;
import com.barberia.repository.PromedioClienteRepository;
import com.barberia.repository.UsuarioRepository;
import com.barberia.service.PromedioClienteService;
import org.springframework.stereotype.Service;

@Service
public class PromedioClienteServiceIMP implements PromedioClienteService {

    private final PromedioClienteRepository promedioRepository;
    private final UsuarioRepository usuarioRepository;
    private final BarberoRepository barberoRepository;

    public PromedioClienteServiceIMP(PromedioClienteRepository promedioRepository,
                                      UsuarioRepository usuarioRepository,
                                      BarberoRepository barberoRepository) {
        this.promedioRepository = promedioRepository;
        this.usuarioRepository = usuarioRepository;
        this.barberoRepository = barberoRepository;
    }

    /* -- buscar promedio de un cliente con un barbero -- */
    @Override
    public PromedioClienteDTO buscarPorUsuarioYBarbero(Long usuarioId, Long barberoId) {
        PromedioCliente p = promedioRepository.findByUsuarioIdAndBarberoId(usuarioId, barberoId)
                .orElseThrow(() -> new RuntimeException("No hay promedio registrado para este cliente y barbero"));
        return toDTO(p);
    }

    /* -- actualizar promedio incremental del cliente -- */
    @Override
    public PromedioClienteDTO actualizarPromedio(Long usuarioId, Long barberoId, long duracionMinutosReal) {
        PromedioCliente p = promedioRepository.findByUsuarioIdAndBarberoId(usuarioId, barberoId)
                .orElseGet(() -> {
                    PromedioCliente nuevo = new PromedioCliente();
                    nuevo.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow());
                    nuevo.setBarbero(barberoRepository.findById(barberoId).orElseThrow());
                    nuevo.setTotalCitas(0);
                    nuevo.setDuracionPromedioMinutos(35.0);
                    return nuevo;
                });
        /* -- formula promedio incremental -- */
        double nuevoPromedio = ((p.getDuracionPromedioMinutos() * p.getTotalCitas()) + duracionMinutosReal)
                / (p.getTotalCitas() + 1);
        p.setDuracionPromedioMinutos(nuevoPromedio);
        p.setTotalCitas(p.getTotalCitas() + 1);
        return toDTO(promedioRepository.save(p));
    }

    /* -- convertir entidad a dto -- */
    private PromedioClienteDTO toDTO(PromedioCliente p) {
        PromedioClienteDTO dto = new PromedioClienteDTO();
        dto.setId(p.getId());
        dto.setUsuarioId(p.getUsuario().getId());
        dto.setBarberoId(p.getBarbero().getId());
        dto.setDuracionPromedioMinutos(p.getDuracionPromedioMinutos());
        dto.setTotalCitas(p.getTotalCitas());
        return dto;
    }
}
