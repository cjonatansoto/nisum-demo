package com.nisum.technical.exercise.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserRequest", description = "DTO para la creación de un usuario con validación de campos y teléfonos asociados")
public class UserRequest implements Serializable {

    @Schema(
            description = "Nombre completo del usuario",
            example = "Jonatan Soto",
            required = true
    )
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "usuario@correo.com",
            required = true
    )
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Email inválido")
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
            message = "El email debe tener un formato válido"
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
            message = "La contraseña debe tener al menos 8 caracteres, incluir letras y números"
    )
    private String password;

    @Schema(
            description = "Lista de teléfonos asociados al usuario",
            required = false
    )
    @Valid
    private List<PhoneRequest> phones;
}

