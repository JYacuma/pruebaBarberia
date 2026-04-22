package com.barberia.dto;

import com.barberia.enums.EstadoCitaEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class CitaDTO {
    private Long id;
    private Long usuarioId;
    private Long barberoId;
    private Long servicioId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private EstadoCitaEnum estado;
}
