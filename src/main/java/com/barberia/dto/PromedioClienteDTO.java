package com.barberia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PromedioClienteDTO {
    private Long id;
    private Long usuarioId;
    private Long barberoId;
    private Double duracionPromedioMinutos;
    private Integer totalCitas;
}
