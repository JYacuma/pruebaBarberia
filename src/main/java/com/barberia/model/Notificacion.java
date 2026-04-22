package com.barberia.model;

import com.barberia.enums.TipoNotificacionEnum;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/* -- tabla: notificacion -- */
@Entity
@Table(name = "notificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {

    /* -- columna: id (clave primaria, autoincremental) -- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /* -- relacion: una notificacion es disparada por una cita -- */
    @ManyToOne
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    /* -- columna: tipo de notificacion (enum) -- */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoNotificacionEnum tipo;

    /* -- columna: mensaje enviado por whatsapp -- */
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    /* -- columna: fecha_envio de la notificacion -- */
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    /* -- columna: enviado indica si el mensaje fue entregado exitosamente -- */
    @Column(name = "enviado", nullable = false)
    private boolean enviado = false;

    /* -- before insert: asigna fecha de envio automaticamente -- */
    @PrePersist
    public void prePersist() {
        this.fechaEnvio = LocalDateTime.now();
    }
}
