package com.barberia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResenaDTO {
    private Long id;
    private Long citaId;
    private Long usuarioId;
    private Long barberoId;
    private Integer calificacion;
    private String comentario;
}
