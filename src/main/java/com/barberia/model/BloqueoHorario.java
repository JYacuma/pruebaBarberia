package com.barberia.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/* -- tabla: bloqueo_horario -- */
@Entity
@Table(name = "bloqueo_horario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BloqueoHorario {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- relacion: muchos bloqueos pertenecen a un barbero -- */
    @ManyToOne
    @JoinColumn(name = "barbero_id", nullable = false)
    private Barbero barbero;

    /* -- columna: fecha_inicio del bloqueo -- */
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    /* -- columna: fecha_fin del bloqueo -- */
    @Column(name = "fecha_fin", nullable = false)
    private LocalDateTime fechaFin;

    /* -- columna: motivo del bloqueo (vacaciones, descanso, etc) -- */
    @Column(name = "motivo")
    private String motivo;
}
