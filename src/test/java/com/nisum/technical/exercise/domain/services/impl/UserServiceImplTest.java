package com.nisum.technical.exercise.domain.services.impl;

import com.nisum.technical.exercise.application.dto.response.PhoneResponse;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.application.mapper.UserMapper;
import com.nisum.technical.exercise.domain.entity.User;
import com.nisum.technical.exercise.domain.exception.EnumError;
import com.nisum.technical.exercise.domain.exception.SimpleException;
import com.nisum.technical.exercise.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserResponse mockUserResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUserResponse = new UserResponse(
                UUID.randomUUID(),
                "Jonatan Soto",
                "usuario@correo.com",
                new ArrayList<PhoneResponse>(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                "fake-token",
                true
        );
    }

    @Test
    void testGetAll_ReturnsUserList() {
        var mockUserEntities = new ArrayList<User>(); // Simulamos entidad de usuario
        mockUserEntities.add(User.builder()
                .id(UUID.randomUUID())
                .name("Jonatan Soto")
                .email("usuario@correo.com")
                .password("encrypted-password")
                .phones(new ArrayList<>())
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now())
                .lastLogin(OffsetDateTime.now())
                .token(null)
                .isActive(true)
                .build());
        when(userRepository.findAll()).thenReturn(mockUserEntities);
        when(userMapper.entityToResponse(any())).thenReturn(mockUserResponse);

        List<UserResponse> users = userService.getAll();

        assertNotNull(users);
        assertEquals(1, users.size());
        assertNull(users.get(0).token()); // token debe ser null
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).entityToResponse(any());
    }

    @Test
    void testGetAll_ThrowsException_WhenEmpty() {
        when(userRepository.findAll()).thenReturn(List.of());

        SimpleException exception = assertThrows(SimpleException.class, () -> userService.getAll());
        assertEquals(EnumError.NO_RESOURCE_FOUND, exception.getErrorEnum());
    }

    @Test
    void testFindById_ReturnsUser() {
        UUID userId = UUID.randomUUID();
        var mockEntity = new User(); // Simulamos entidad de usuario
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockEntity));
        when(userMapper.entityToResponse(mockEntity)).thenReturn(mockUserResponse);

        UserResponse user = userService.findById(userId);

        assertNotNull(user);
        assertEquals(mockUserResponse.id(), user.id());
        assertNull(user.token());
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).entityToResponse(mockEntity);
    }

    @Test
    void testFindById_ThrowsException_WhenNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        SimpleException exception = assertThrows(SimpleException.class, () -> userService.findById(userId));
        assertEquals(EnumError.NO_RESOURCE_FOUND, exception.getErrorEnum());
    }
}
