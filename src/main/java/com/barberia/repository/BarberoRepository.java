package com.barberia.repository;

import com.barberia.model.Barbero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/* -- repositorio de barbero -- */
public interface BarberoRepository extends JpaRepository<Barbero, Long> {
    /* -- listar solo barberos activos -- */
    List<Barbero> findByActivoTrue();
}
