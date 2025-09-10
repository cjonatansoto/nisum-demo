package com.nisum.technical.exercise.domain.services.impl;

import com.nisum.technical.exercise.application.dto.request.LoginRequest;
import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.exception.EnumError;
import com.nisum.technical.exercise.domain.exception.SimpleException;
import com.nisum.technical.exercise.application.mapper.UserMapper;
import com.nisum.technical.exercise.infrastructure.repository.UserRepository;
import com.nisum.technical.exercise.application.util.JwtUtil;
import com.nisum.technical.exercise.domain.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;


@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public UserResponse login(LoginRequest loginRequest) {

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    log.warn("Usuario no encontrado: email={}", loginRequest.getEmail());
                    return new SimpleException(EnumError.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value());
                });

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.warn("Credenciales invalidas: email={}", loginRequest.getEmail());
            throw new SimpleException(EnumError.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value());
        }

        var now = OffsetDateTime.now();
        var token = jwtUtil.generateToken(user);

        // Actualizamos lastLogin y token antes de persistir
        user.setLastLogin(now);
        user.setToken(token);
        userRepository.save(user);

        log.info("Login exitoso, Usuario autenticado: id={}, email={}", user.getId(), user.getEmail());

        return userMapper.entityToResponse(user);
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest userRequest) {
        userRepository.findByEmail(userRequest.getEmail()).ifPresent(existing -> {
            log.warn("Intento de registro con correo duplicado: email={}", userRequest.getEmail());
            throw new SimpleException(EnumError.DUPLICATE_EMAIL, HttpStatus.CONFLICT.value());
        });

        var user = userMapper.requestToEntity(userRequest);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setLastLogin(OffsetDateTime.now());
        user.setToken(jwtUtil.generateToken(user));

        var savedUser = userRepository.saveAndFlush(user);

        log.info("Usuario registrado correctamente: id={}, email={}", savedUser.getId(), savedUser.getEmail());

        return userMapper.entityToResponse(savedUser);
    }

}
