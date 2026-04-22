package com.barberia.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/* -- tabla: promedio_cliente -- */
@Entity
@Table(name = "promedio_cliente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromedioCliente {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- relacion: promedio pertenece a un usuario (cliente) -- */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /* -- relacion: promedio es calculado por barbero especifico -- */
    @ManyToOne
    @JoinColumn(name = "barbero_id", nullable = false)
    private Barbero barbero;

    /* -- columna: duracion_promedio_minutos calculada automaticamente -- */
    @Column(name = "duracion_promedio_minutos", nullable = false)
    private Double duracionPromedioMinutos = 35.0;

    /* -- columna: total_citas usadas para calcular el promedio -- */
    @Column(name = "total_citas", nullable = false)
    private Integer totalCitas = 0;

    /* -- columna: ultima_actualizacion del promedio -- */
    @Column(name = "ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    /* -- before insert / update: actualiza timestamp automaticamente -- */
    @PrePersist
    @PreUpdate
    public void preUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }
}
