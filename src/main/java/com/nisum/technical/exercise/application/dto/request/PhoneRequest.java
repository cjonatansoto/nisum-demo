package com.nisum.technical.exercise.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "PhoneRequest", description = "DTO para representar un teléfono asociado a un usuario")
public class PhoneRequest implements Serializable {

    @Schema(
            description = "Número de teléfono",
            example = "987654321",
            required = true
    )
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "El número de teléfono debe contener entre 7 y 15 dígitos")
    private String number;

    @Schema(
            description = "Código de ciudad",
            example = "2",
            required = true
    )
    @NotBlank(message = "El código de ciudad no puede estar vacío")
    @Pattern(regexp = "^[0-9]{1,5}$", message = "El código de ciudad debe contener entre 1 y 5 dígitos")
    private String cityCode;

    @Schema(
            description = "Código de país (prefijo internacional)",
            example = "56",
            required = true
    )
    @NotBlank(message = "El código de país no puede estar vacío")
    @Pattern(regexp = "^[0-9]{1,5}$", message = "El código de país debe contener entre 1 y 5 dígitos")
    private String countryCode;
}
