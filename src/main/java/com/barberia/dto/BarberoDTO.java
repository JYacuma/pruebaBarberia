package com.barberia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BarberoDTO {
    private Long id;
    private String nombre;
    private String especialidad;
    private String telefono;
    private boolean activo;
    private Long usuarioId;
}
