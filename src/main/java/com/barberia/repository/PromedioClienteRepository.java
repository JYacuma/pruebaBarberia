package com.barberia.repository;

import com.barberia.model.PromedioCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/* -- repositorio de promedio cliente -- */
public interface PromedioClienteRepository extends JpaRepository<PromedioCliente, Long> {
    /* -- buscar promedio de un cliente con un barbero especifico -- */
    Optional<PromedioCliente> findByUsuarioIdAndBarberoId(Long usuarioId, Long barberoId);
}
