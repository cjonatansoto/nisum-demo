package com.nisum.technical.exercise.infrastructure.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.technical.exercise.domain.exception.ApiErrorResponse;
import com.nisum.technical.exercise.domain.exception.EnumError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    private final MessageSource messageSource;

    public UnauthorizedHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(ApiErrorResponse.builder()
                .mensaje(messageSource.getMessage(EnumError.UNAUTHORIZED.getMessageKey(),
                        null,
                        LocaleContextHolder.getLocale()))
                .build()));
    }
}
