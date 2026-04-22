package com.barberia.dto;

import com.barberia.enums.RolEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private String password;
    private RolEnum rol;
    private boolean activo;
}
