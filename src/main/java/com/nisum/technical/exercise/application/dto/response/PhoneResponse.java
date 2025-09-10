package com.nisum.technical.exercise.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PhoneResponse", description = "DTO para representar un teléfono asociado a un usuario")
public record PhoneResponse(
        @Schema(description = "Número de teléfono", example = "987654321")
        String number,

        @Schema(description = "Código de ciudad", example = "2")
        String cityCode,

        @Schema(description = "Código de país (prefijo internacional)", example = "56")
        String countryCode
) {
}
