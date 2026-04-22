package com.barberia.repository;

import com.barberia.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/* -- repositorio de resena -- */
public interface ResenaRepository extends JpaRepository<Resena, Long> {
    /* -- buscar resenas de un barbero -- */
    List<Resena> findByBarberoId(Long barberoId);
    /* -- verificar si ya existe resena para una cita -- */
    Optional<Resena> findByCitaId(Long citaId);
}
