package com.nisum.technical.exercise.domain.services.impl;

import com.nisum.technical.exercise.application.dto.response.UserResponse;
import com.nisum.technical.exercise.domain.exception.EnumError;
import com.nisum.technical.exercise.domain.exception.SimpleException;
import com.nisum.technical.exercise.application.mapper.UserMapper;
import com.nisum.technical.exercise.infrastructure.repository.UserRepository;
import com.nisum.technical.exercise.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getAll() {
        var users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new SimpleException(EnumError.NO_RESOURCE_FOUND);
        }

        return users.stream()
                .map(user -> {
                    var response = userMapper.entityToResponse(user);
                    return new UserResponse(
                            response.id(),
                            response.name(),
                            response.email(),
                            response.phones(),
                            response.created(),
                            response.modified(),
                            response.lastLogin(),
                            null,            // token oculto
                            response.isActive() // mantiene active
                    );
                })
                .toList();
    }


    @Override
    public UserResponse findById(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new SimpleException(EnumError.NO_RESOURCE_FOUND));
        log.info("Usuario encontrado: id={}, email={}", userId, user.getEmail());
        var response = userMapper.entityToResponse(user);
        return new UserResponse(
                response.id(),
                response.name(),
                response.email(),
                response.phones(),
                response.created(),
                response.modified(),
                response.lastLogin(),
                null,   // no mostrar token
                response.isActive()
        );
    }
}

