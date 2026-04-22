package com.barberia.serviceIMP;

import com.barberia.dto.UsuarioDTO;
import com.barberia.model.Usuario;
import com.barberia.repository.UsuarioRepository;
import com.barberia.service.UsuarioService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceIMP implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceIMP(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /* -- listar todos los usuarios -- */
    @Override
    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /* -- buscar usuario por id -- */
    @Override
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return toDTO(usuario);
    }

    /* -- crear nuevo usuario -- */
    @Override
    public UsuarioDTO crear(UsuarioDTO dto) {
        Usuario usuario = toEntity(dto);
        return toDTO(usuarioRepository.save(usuario));
    }

    /* -- actualizar usuario existente -- */
    @Override
    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(dto.getRol());
        usuario.setActivo(dto.isActivo());
        return toDTO(usuarioRepository.save(usuario));
    }

    /* -- eliminar usuario por id -- */
    @Override
    public void eliminar(Long id) {
        usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        usuarioRepository.deleteById(id);
    }

    /* -- convertir entidad a dto -- */
    private UsuarioDTO toDTO(Usuario u) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(u.getId());
        dto.setNombre(u.getNombre());
        dto.setCorreo(u.getCorreo());
        dto.setTelefono(u.getTelefono());
        dto.setRol(u.getRol());
        dto.setActivo(u.isActivo());
        return dto;
    }

    /* -- convertir dto a entidad -- */
    private Usuario toEntity(UsuarioDTO dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setCorreo(dto.getCorreo());
        u.setTelefono(dto.getTelefono());
        u.setPassword(dto.getPassword());
        u.setRol(dto.getRol());
        u.setActivo(dto.isActivo());
        return u;
    }
}
