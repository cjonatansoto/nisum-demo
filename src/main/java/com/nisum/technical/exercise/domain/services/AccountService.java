package com.nisum.technical.exercise.domain.services;

import com.nisum.technical.exercise.application.dto.request.LoginRequest;
import com.nisum.technical.exercise.application.dto.request.UserRequest;
import com.nisum.technical.exercise.application.dto.response.UserResponse;

public interface AccountService {
    UserResponse login(LoginRequest loginRequest);
    UserResponse register(UserRequest userRequest);
}
