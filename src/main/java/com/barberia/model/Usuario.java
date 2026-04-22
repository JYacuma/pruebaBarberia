package com.barberia.model;

import com.barberia.enums.RolEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/* -- tabla: usuario -- */
@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- columna: nombre -- */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /* -- columna: correo (unico) -- */
    @Column(name = "correo", unique = true)
    private String correo;

    /* -- columna: telefono (unico) -- */
    @Column(name = "telefono", unique = true)
    private String telefono;

    /* -- columna: password -- */
    @Column(name = "password", nullable = false)
    private String password;

    /* -- columna: rol (enum) -- */
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolEnum rol;

    /* -- columna: activo -- */
    @Column(name = "activo", nullable = false)
    private boolean activo = true;

    /* -- columna: fecha_registro -- */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    /* -- before insert: asigna fecha de registro automaticamente -- */
    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
    }
}
