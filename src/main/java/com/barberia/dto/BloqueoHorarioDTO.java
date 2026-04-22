package com.barberia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BloqueoHorarioDTO {
    private Long id;
    private Long barberoId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String motivo;
}
