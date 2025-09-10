package com.nisum.technical.exercise.presentation;

import com.nisum.technical.exercise.application.dto.request.LoginRequest;
import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class AccountController {

    private final AccountService accountService;

    /**
     * Endpoint for user registration.
     *
     * @param userRequest User registration data
     * @return Registered user info with JWT
     */
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns the user info with JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully registered"),
            @ApiResponse(responseCode = "409", description = "Email already exists"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = accountService.register(userRequest);
        return ResponseEntity.ok(userResponse);
    }

    /**
     * Endpoint for user login.
     *
     * @param loginRequest User login credentials
     * @return User info with JWT
     */
    @Operation(summary = "User login", description = "Authenticates a user and returns the user info with JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse userResponse = accountService.login(loginRequest);
        return ResponseEntity.ok(userResponse);
    }
}

