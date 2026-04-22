package com.barberia.repository;

import com.barberia.model.BloqueoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

/* -- repositorio de bloqueo horario -- */
public interface BloqueoHorarioRepository extends JpaRepository<BloqueoHorario, Long> {
    /* -- buscar bloqueos de un barbero -- */
    List<BloqueoHorario> findByBarberoId(Long barberoId);
    /* -- verificar si un barbero tiene bloqueo en un rango de tiempo -- */
    @Query("SELECT b FROM BloqueoHorario b WHERE b.barbero.id = :barberoId " +
           "AND b.fechaInicio < :fin AND b.fechaFin > :inicio")
    List<BloqueoHorario> findBloqueoEnRango(@Param("barberoId") Long barberoId,
                                             @Param("inicio") LocalDateTime inicio,
                                             @Param("fin") LocalDateTime fin);
}
