package com.nisum.technical.exercise.presentation;

import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Usuarios", description = "API para gestión de usuarios")
public class UserController {

    private final UserService userService;

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return Lista de usuarios
     */
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve la lista completa de usuarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        log.info("Request received: get all users");
        List<UserResponse> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    /**
     * Busca un usuario por su UUID.
     *
     * @param userId UUID del usuario
     * @return Usuario encontrado
     */
    @Operation(summary = "Buscar usuario por ID", description = "Devuelve la información de un usuario dado su UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID userId) {
        log.info("Request received: get user by ID {}", userId);
        UserResponse user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }
}
