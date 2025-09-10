package com.nisum.technical.exercise.domain.services.impl;

import com.nisum.technical.exercise.application.dto.request.LoginRequest;
import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.application.mapper.UserMapper;
import com.nisum.technical.exercise.domain.exception.EnumError;
import com.nisum.technical.exercise.domain.exception.SimpleException;
import com.nisum.technical.exercise.infrastructure.repository.UserRepository;
import com.nisum.technical.exercise.application.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AccountServiceImpl accountService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        var userEntity = org.mockito.Mockito.mock(com.nisum.technical.exercise.domain.entity.User.class);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userEntity));
        when(userEntity.getPassword()).thenReturn(passwordEncoder.encode("password123"));
        when(jwtUtil.generateToken(userEntity)).thenReturn("token123");
        var response = new UserResponse();
        when(userMapper.entityToResponse(userEntity)).thenReturn(response);

        UserResponse result = accountService.login(request);

        assertNotNull(result);
        verify(userRepository).save(userEntity);
        assertEquals(response, result);
        verify(userEntity).setLastLogin(any(OffsetDateTime.class));
        verify(userEntity).setToken("token123");
    }

    @Test
    void loginUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("missing@example.com");
        request.setPassword("password123");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        SimpleException ex = assertThrows(SimpleException.class, () -> accountService.login(request));
        assertEquals(EnumError.UNAUTHORIZED, ex.getErrorEnum());
        assertEquals(401, ex.getStatus());
    }

    @Test
    void loginInvalidPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpass");

        var userEntity = org.mockito.Mockito.mock(com.nisum.technical.exercise.domain.entity.User.class);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(userEntity));
        when(userEntity.getPassword()).thenReturn(passwordEncoder.encode("correctpass"));

        SimpleException ex = assertThrows(SimpleException.class, () -> accountService.login(request));
        assertEquals(EnumError.UNAUTHORIZED, ex.getErrorEnum());
        assertEquals(401, ex.getStatus());
    }

    @Test
    void registerSuccess() {
        UserRequest request = new UserRequest();
        request.setEmail("new@example.com");
        request.setPassword("password123");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        var userEntity = org.mockito.Mockito.mock(com.nisum.technical.exercise.domain.entity.User.class);
        when(userMapper.requestToEntity(request)).thenReturn(userEntity);
        when(jwtUtil.generateToken(userEntity)).thenReturn("token123");
        var savedUser = org.mockito.Mockito.mock(com.nisum.technical.exercise.domain.entity.User.class);
        when(userRepository.saveAndFlush(userEntity)).thenReturn(savedUser);
        var response = new UserResponse();
        when(userMapper.entityToResponse(savedUser)).thenReturn(response);

        UserResponse result = accountService.register(request);

        assertNotNull(result);
        verify(userRepository).saveAndFlush(userEntity);
        assertEquals(response, result);
        verify(userEntity).setPassword(anyString());
        verify(userEntity).setLastLogin(any(OffsetDateTime.class));
        verify(userEntity).setToken("token123");
    }

    @Test
    void registerDuplicateEmail() {
        UserRequest request = new UserRequest();
        request.setEmail("existing@example.com");
        request.setPassword("password123");

        var existingUser = org.mockito.Mockito.mock(com.nisum.technical.exercise.domain.entity.User.class);
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(existingUser));

        SimpleException ex = assertThrows(SimpleException.class, () -> accountService.register(request));
        assertEquals(EnumError.DUPLICATE_EMAIL, ex.getErrorEnum());
        assertEquals(409, ex.getStatus());
    }
}
