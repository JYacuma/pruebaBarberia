package com.barberia.model;

import jakarta.persistence.*;
import lombok.*;

/* -- tabla: barbero -- */
@Entity
@Table(name = "barbero")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Barbero {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- columna: nombre -- */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /* -- columna: especialidad -- */
    @Column(name = "especialidad")
    private String especialidad;

    /* -- columna: telefono -- */
    @Column(name = "telefono")
    private String telefono;

    /* -- columna: activo -- */
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    /* -- relacion: un barbero puede ser un usuario del sistema -- */
    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}
