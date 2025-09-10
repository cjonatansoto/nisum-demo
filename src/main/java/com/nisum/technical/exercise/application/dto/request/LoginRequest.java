package com.nisum.technical.exercise.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "LoginRequest", description = "DTO para iniciar sesión, con correo y contraseña")
public class LoginRequest implements Serializable {

    @Schema(
            description = "Correo electrónico del usuario",
            example = "usuario@correo.com",
            required = true
    )
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Correo inválido")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "El correo debe tener un formato válido"
    )
    private String email;

    @Schema(
            description = "Contraseña del usuario. Debe tener al menos 8 caracteres, incluyendo letras y números",
            example = "Password123",
            required = true
    )
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$",
            message = "La contraseña debe tener al menos 8 caracteres e incluir letras y números"
    )
    private String password;
}
