package com.barberia.repository;

import com.barberia.enums.DiaSemanaEnum;
import com.barberia.model.HorarioBarbero;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/* -- repositorio de horario barbero -- */
public interface HorarioBarberoRepository extends JpaRepository<HorarioBarbero, Long> {
    /* -- buscar horarios de un barbero -- */
    List<HorarioBarbero> findByBarberoId(Long barberoId);
    /* -- buscar horarios de un barbero por dia -- */
    List<HorarioBarbero> findByBarberoIdAndDiaSemana(Long barberoId, DiaSemanaEnum diaSemana);
}
