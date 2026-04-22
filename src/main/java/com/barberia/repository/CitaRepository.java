package com.barberia.repository;

import com.barberia.enums.EstadoCitaEnum;
import com.barberia.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

/* -- repositorio de cita -- */
public interface CitaRepository extends JpaRepository<Cita, Long> {
    /* -- buscar citas de un barbero en una fecha especifica -- */
    List<Cita> findByBarberoIdAndFecha(Long barberoId, LocalDate fecha);
    /* -- buscar citas de un usuario cliente -- */
    List<Cita> findByUsuarioId(Long usuarioId);
    /* -- buscar citas por estado -- */
    List<Cita> findByEstado(EstadoCitaEnum estado);
    /* -- buscar citas activas de un barbero en una fecha -- */
    List<Cita> findByBarberoIdAndFechaAndEstadoNot(Long barberoId, LocalDate fecha, EstadoCitaEnum estado);
}
