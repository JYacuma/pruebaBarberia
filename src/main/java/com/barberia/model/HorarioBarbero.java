package com.barberia.model;

import com.barberia.enums.DiaSemanaEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

/* -- tabla: horario_barbero -- */
@Entity
@Table(name = "horario_barbero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HorarioBarbero {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- relacion: muchos horarios pertenecen a un barbero -- */
    @ManyToOne
    @JoinColumn(name = "barbero_id", nullable = false)
    private Barbero barbero;

    /* -- columna: dia_semana (enum) -- */
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana", nullable = false)
    private DiaSemanaEnum diaSemana;

    /* -- columna: hora_inicio del turno del barbero -- */
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    /* -- columna: hora_fin del turno del barbero -- */
    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;
}
