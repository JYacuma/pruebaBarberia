package com.barberia.dto;

import com.barberia.enums.DiaSemanaEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class HorarioBarberoDTO {
    private Long id;
    private Long barberoId;
    private DiaSemanaEnum diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
