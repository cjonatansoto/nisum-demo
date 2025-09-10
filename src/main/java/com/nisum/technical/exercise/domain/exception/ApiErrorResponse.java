package com.nisum.technical.exercise.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "ApiErrorResponse", description = "Respuesta de error estándar")
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ApiErrorResponse {

    @Schema(example = "Parámetros inválidos en la solicitud", description = "Mensaje de error")
    private final String mensaje;
}

