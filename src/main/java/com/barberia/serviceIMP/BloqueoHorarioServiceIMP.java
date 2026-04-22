package com.barberia.serviceIMP;

import com.barberia.dto.BloqueoHorarioDTO;
import com.barberia.model.Barbero;
import com.barberia.model.BloqueoHorario;
import com.barberia.repository.BarberoRepository;
import com.barberia.repository.BloqueoHorarioRepository;
import com.barberia.service.BloqueoHorarioService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloqueoHorarioServiceIMP implements BloqueoHorarioService {

    private final BloqueoHorarioRepository bloqueoRepository;
    private final BarberoRepository barberoRepository;

    public BloqueoHorarioServiceIMP(BloqueoHorarioRepository bloqueoRepository,
                                     BarberoRepository barberoRepository) {
        this.bloqueoRepository = bloqueoRepository;
        this.barberoRepository = barberoRepository;
    }

    /* -- listar bloqueos de un barbero -- */
    @Override
    public List<BloqueoHorarioDTO> listarPorBarbero(Long barberoId) {
        return bloqueoRepository.findByBarberoId(barberoId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- buscar bloqueo por id -- */
    @Override
    public BloqueoHorarioDTO buscarPorId(Long id) {
        BloqueoHorario b = bloqueoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloqueo no encontrado con id: " + id));
        return toDTO(b);
    }

    /* -- crear bloqueo de horario -- */
    @Override
    public BloqueoHorarioDTO crear(BloqueoHorarioDTO dto) {
        Barbero barbero = barberoRepository.findById(dto.getBarberoId())
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + dto.getBarberoId()));
        BloqueoHorario b = new BloqueoHorario();
        b.setBarbero(barbero);
        b.setFechaInicio(dto.getFechaInicio());
        b.setFechaFin(dto.getFechaFin());
        b.setMotivo(dto.getMotivo());
        return toDTO(bloqueoRepository.save(b));
    }

    /* -- eliminar bloqueo -- */
    @Override
    public void eliminar(Long id) {
        bloqueoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloqueo no encontrado con id: " + id));
        bloqueoRepository.deleteById(id);
    }

    /* -- convertir entidad a dto -- */
    private BloqueoHorarioDTO toDTO(BloqueoHorario b) {
        BloqueoHorarioDTO dto = new BloqueoHorarioDTO();
        dto.setId(b.getId());
        dto.setBarberoId(b.getBarbero().getId());
        dto.setFechaInicio(b.getFechaInicio());
        dto.setFechaFin(b.getFechaFin());
        dto.setMotivo(b.getMotivo());
        return dto;
    }
}
