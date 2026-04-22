package com.barberia.serviceIMP;

import com.barberia.dto.ResenaDTO;
import com.barberia.model.*;
import com.barberia.repository.*;
import com.barberia.service.ResenaService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResenaServiceIMP implements ResenaService {

    private final ResenaRepository resenaRepository;
    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final BarberoRepository barberoRepository;

    public ResenaServiceIMP(ResenaRepository resenaRepository,
                             CitaRepository citaRepository,
                             UsuarioRepository usuarioRepository,
                             BarberoRepository barberoRepository) {
        this.resenaRepository = resenaRepository;
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.barberoRepository = barberoRepository;
    }

    /* -- listar resenas de un barbero -- */
    @Override
    public List<ResenaDTO> listarPorBarbero(Long barberoId) {
        return resenaRepository.findByBarberoId(barberoId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- buscar resena por id -- */
    @Override
    public ResenaDTO buscarPorId(Long id) {
        Resena r = resenaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resena no encontrada con id: " + id));
        return toDTO(r);
    }

    /* -- crear resena para una cita finalizada -- */
    @Override
    public ResenaDTO crear(ResenaDTO dto) {
        resenaRepository.findByCitaId(dto.getCitaId()).ifPresent(r -> {
            throw new RuntimeException("Esta cita ya tiene una resena registrada");
        });
        Cita cita = citaRepository.findById(dto.getCitaId())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + dto.getCitaId()));
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        Barbero barbero = barberoRepository.findById(dto.getBarberoId())
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + dto.getBarberoId()));
        Resena r = new Resena();
        r.setCita(cita);
        r.setUsuario(usuario);
        r.setBarbero(barbero);
        r.setCalificacion(dto.getCalificacion());
        r.setComentario(dto.getComentario());
        return toDTO(resenaRepository.save(r));
    }

    /* -- convertir entidad a dto -- */
    private ResenaDTO toDTO(Resena r) {
        ResenaDTO dto = new ResenaDTO();
        dto.setId(r.getId());
        dto.setCitaId(r.getCita().getId());
        dto.setUsuarioId(r.getUsuario().getId());
        dto.setBarberoId(r.getBarbero().getId());
        dto.setCalificacion(r.getCalificacion());
        dto.setComentario(r.getComentario());
        return dto;
    }
}
