package com.barberia.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/* -- tabla: resena -- */
@Entity
@Table(name = "resena")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resena {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- relacion: una resena pertenece a una cita -- */
    @OneToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    /* -- relacion: una resena es escrita por un usuario -- */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /* -- relacion: una resena es recibida por un barbero -- */
    @ManyToOne
    @JoinColumn(name = "barbero_id", nullable = false)
    private Barbero barbero;

    /* -- columna: calificacion (1 a 5) -- */
    @Column(name = "calificacion", nullable = false)
    private Integer calificacion;

    /* -- columna: comentario opcional -- */
    @Column(name = "comentario")
    private String comentario;

    /* -- columna: fecha de la resena -- */
    @Column(name = "fecha")
    private LocalDateTime fecha;

    /* -- before insert: asigna fecha automaticamente -- */
    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
    }
}
