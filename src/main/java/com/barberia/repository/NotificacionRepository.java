package com.barberia.repository;

import com.barberia.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/* -- repositorio de notificacion -- */
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    /* -- buscar notificaciones de una cita -- */
    List<Notificacion> findByCitaId(Long citaId);
}
