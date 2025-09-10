package com.nisum.technical.exercise.domain.services.impl;

import com.nisum.technical.exercise.domain.exception.EnumError;
import com.nisum.technical.exercise.domain.exception.SimpleException;
import com.nisum.technical.exercise.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found in database [email={}]");
                    return new SimpleException(EnumError.NO_RESOURCE_FOUND);
                });
    }


}

