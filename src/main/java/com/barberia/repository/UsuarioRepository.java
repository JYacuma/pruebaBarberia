package com.barberia.repository;

import com.barberia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/* -- repositorio de usuario -- */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /* -- buscar usuario por correo -- */
    Optional<Usuario> findByCorreo(String correo);
    /* -- buscar usuario por telefono -- */
    Optional<Usuario> findByTelefono(String telefono);
}
