package org.bedu.servidores.model.builders;
import lombok.Setter;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;

public class RespuestaError {
    @Getter
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Getter
    @Setter
    private int estatus;

    @Getter
    @Setter
    private Map<String,String> errores;

    @Getter
    @Setter
    private String ruta;
}