package com.nisum.technical.exercise.presentation;

import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponse userResponse;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userId = UUID.randomUUID();
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
    void testGetAllUsers() {
        when(userService.getAll()).thenReturn(List.of(userResponse));
        ResponseEntity<List<UserResponse>> response = userController.getAllUsers();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(userResponse.email(), response.getBody().get(0).email());
    }

    @Test
    void testGetUserById() {
        when(userService.findById(any(UUID.class))).thenReturn(userResponse);
        ResponseEntity<UserResponse> response = userController.getUserById(userId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userResponse.email(), response.getBody().email());
        assertEquals(userResponse.token(), response.getBody().token());
    }
}
