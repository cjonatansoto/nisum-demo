package com.nisum.technical.exercise.presentation;

import com.nisum.technical.exercise.application.dto.request.LoginRequest;
import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private UserRequest userRequest;
    private LoginRequest loginRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequest = UserRequest.builder()
                .name("Juan Perez")
                .email("juan@example.com")
                .password("Password123!")
                .build();

        loginRequest = LoginRequest.builder()
                .email("juan@example.com")
                .password("Password123!")
                .build();

        userResponse = new UserResponse(
                UUID.randomUUID(),
                "Juan Perez",
                "juan@example.com",
                new ArrayList<>(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                "token-falso",
                true);
    }

    @Test
    void testRegisterSuccess() {
        when(accountService.register(any(UserRequest.class))).thenReturn(userResponse);
        ResponseEntity<UserResponse> response = accountController.register(userRequest);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userResponse.email(), response.getBody().email());
        assertEquals(userResponse.token(), response.getBody().token());
    }

    @Test
    void testLoginSuccess() {
        when(accountService.login(any(LoginRequest.class))).thenReturn(userResponse);
        ResponseEntity<UserResponse> response = accountController.login(loginRequest);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userResponse.email(), response.getBody().email());
        assertEquals(userResponse.token(), response.getBody().token());
    }
}
