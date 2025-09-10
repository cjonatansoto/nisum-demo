package com.nisum.technical.exercise.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Schema(name = "UserResponse", description = "DTO de respuesta con la información de un usuario registrado")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        @Schema(description = "Identificador único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Nombre completo del usuario", example = "Jonatan Soto")
        String name,

        @Schema(description = "Correo electrónico del usuario", example = "usuario@correo.com")
        String email,

        @Schema(description = "Lista de teléfonos asociados al usuario")
        List<PhoneResponse> phones,

        @Schema(description = "Fecha y hora de creación del usuario", example = "09-09-2025 18:00:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        OffsetDateTime created,

        @Schema(description = "Fecha y hora de última modificación del usuario", example = "09-09-2025 18:05:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        OffsetDateTime modified,

        @Schema(description = "Fecha y hora del último inicio de sesión", example = "09-09-2025 18:10:00")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        OffsetDateTime lastLogin,

        @Schema(description = "Token JWT generado al registrar o iniciar sesión", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token,

        @Schema(description = "Indica si el usuario está activo", example = "true")
        boolean isActive
) {
    public UserResponse() {
        this(null, null, null, new ArrayList<>(), null, null, null, null, false);
    }
}
