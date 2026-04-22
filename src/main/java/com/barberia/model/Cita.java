package com.barberia.model;

import com.barberia.enums.EstadoCitaEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/* -- tabla: cita -- */
@Entity
@Table(name = "cita")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- relacion: muchas citas pertenecen a un usuario (cliente) -- */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /* -- relacion: muchas citas pertenecen a un barbero -- */
    @ManyToOne
    @JoinColumn(name = "barbero_id", nullable = false)
    private Barbero barbero;

    /* -- relacion: muchas citas incluyen un servicio -- */
    @ManyToOne
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    /* -- columna: fecha de la cita -- */
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /* -- columna: hora_inicio estimada de la cita -- */
    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    /* -- columna: hora_fin estimada segun promedio del cliente -- */
    @Column(name = "hora_fin")
    private LocalTime horaFin;

    /* -- columna: estado de la cita (enum) -- */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCitaEnum estado = EstadoCitaEnum.PENDIENTE;

    /* -- columna: hora_inicio_real cuando el barbero confirma inicio -- */
    @Column(name = "hora_inicio_real")
    private LocalDateTime horaInicioReal;

    /* -- columna: hora_fin_real cuando el barbero confirma fin -- */
    @Column(name = "hora_fin_real")
    private LocalDateTime horaFinReal;

    /* -- columna: indica si el cliente no se presento -- */
    @Column(name = "no_presento", nullable = false)
    private boolean noPresento = false;
}
