package com.nisum.technical.exercise.domain.services;

import com.nisum.technical.exercise.application.dto.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponse> getAll();
    UserResponse findById(UUID userId);
}
