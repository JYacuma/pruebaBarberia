package com.barberia.model;

import jakarta.persistence.*;
import lombok.*;

/* -- tabla: servicio -- */
@Entity
@Table(name = "servicio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servicio {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- columna: nombre -- */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /* -- columna: descripcion -- */
    @Column(name = "descripcion")
    private String descripcion;

    /* -- columna: precio -- */
    @Column(name = "precio", nullable = false)
    private Double precio;

    /* -- columna: duracion_minutos (duracion estimada del servicio) -- */
    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;
}
