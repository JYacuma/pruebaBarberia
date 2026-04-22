package com.barberia.serviceIMP;

import com.barberia.dto.BarberoDTO;
import com.barberia.model.Barbero;
import com.barberia.model.Usuario;
import com.barberia.repository.BarberoRepository;
import com.barberia.repository.UsuarioRepository;
import com.barberia.service.BarberoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BarberoServiceIMP implements BarberoService {

    private final BarberoRepository barberoRepository;
    private final UsuarioRepository usuarioRepository;

    public BarberoServiceIMP(BarberoRepository barberoRepository,
                              UsuarioRepository usuarioRepository) {
        this.barberoRepository = barberoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /* -- listar todos los barberos -- */
    @Override
    public List<BarberoDTO> listarTodos() {
        return barberoRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- listar solo barberos activos -- */
    @Override
    public List<BarberoDTO> listarActivos() {
        return barberoRepository.findByActivoTrue()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    /* -- buscar barbero por id -- */
    @Override
    public BarberoDTO buscarPorId(Long id) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + id));
        return toDTO(barbero);
    }

    /* -- crear nuevo barbero -- */
    @Override
    public BarberoDTO crear(BarberoDTO dto) {
        Barbero barbero = toEntity(dto);
        return toDTO(barberoRepository.save(barbero));
    }

    /* -- actualizar barbero existente -- */
    @Override
    public BarberoDTO actualizar(Long id, BarberoDTO dto) {
        Barbero barbero = barberoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + id));
        barbero.setNombre(dto.getNombre());
        barbero.setEspecialidad(dto.getEspecialidad());
        barbero.setTelefono(dto.getTelefono());
        barbero.setActivo(dto.isActivo());
        return toDTO(barberoRepository.save(barbero));
    }

    /* -- eliminar barbero por id -- */
    @Override
    public void eliminar(Long id) {
        barberoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + id));
        barberoRepository.deleteById(id);
    }

    /* -- convertir entidad a dto -- */
    private BarberoDTO toDTO(Barbero b) {
        BarberoDTO dto = new BarberoDTO();
        dto.setId(b.getId());
        dto.setNombre(b.getNombre());
        dto.setEspecialidad(b.getEspecialidad());
        dto.setTelefono(b.getTelefono());
        dto.setActivo(b.isActivo());
        if (b.getUsuario() != null) dto.setUsuarioId(b.getUsuario().getId());
        return dto;
    }

    /* -- convertir dto a entidad -- */
    private Barbero toEntity(BarberoDTO dto) {
        Barbero b = new Barbero();
        b.setNombre(dto.getNombre());
        b.setEspecialidad(dto.getEspecialidad());
        b.setTelefono(dto.getTelefono());
        b.setActivo(dto.isActivo());
        if (dto.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));
            b.setUsuario(usuario);
        }
        return b;
    }
}
