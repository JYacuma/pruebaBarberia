package com.barberia.dto;

import com.barberia.enums.TipoNotificacionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificacionDTO {
    private Long id;
    private Long citaId;
    private TipoNotificacionEnum tipo;
    private String mensaje;
    private boolean enviado;
}
