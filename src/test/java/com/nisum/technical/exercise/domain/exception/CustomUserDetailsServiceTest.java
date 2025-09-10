package com.nisum.technical.exercise.domain.exception;

import com.nisum.technical.exercise.domain.entity.User;
import com.nisum.technical.exercise.domain.services.impl.CustomUserDetailsService;
import com.nisum.technical.exercise.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = User.builder()
                .id(UUID.randomUUID())
                .name("Jonatan Soto")
                .email("usuario@correo.com")
                .password("encrypted-password")
                .build();
    }

    @Test
    void loadUserByUsernameReturnsUserDetailsWhenUserExists() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        UserDetails userDetails = userDetailsService.loadUserByUsername(mockUser.getEmail());
        assertNotNull(userDetails);
        assertEquals(mockUser.getEmail(), userDetails.getUsername());
        assertEquals(mockUser.getPassword(), userDetails.getPassword());
        verify(userRepository, times(1)).findByEmail(mockUser.getEmail());
    }

    @Test
    void loadUserByUsernameThrowsException_WhenUserDoesNotExist() {
        String email = "no-existe@correo.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        SimpleException exception = assertThrows(SimpleException.class, () -> {
            userDetailsService.loadUserByUsername(email);
        });
        assertEquals(EnumError.NO_RESOURCE_FOUND, exception.getErrorEnum());
        verify(userRepository, times(1)).findByEmail(email);
    }
}
