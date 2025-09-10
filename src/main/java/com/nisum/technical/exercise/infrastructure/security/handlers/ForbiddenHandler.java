package com.nisum.technical.exercise.infrastructure.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.technical.exercise.domain.exception.ApiErrorResponse;
import com.nisum.technical.exercise.domain.exception.EnumError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ForbiddenHandler implements AccessDeniedHandler {
    private final MessageSource messageSource;

    public ForbiddenHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(new ObjectMapper().writeValueAsString(ApiErrorResponse.builder()
                .mensaje(messageSource.getMessage(EnumError.FORBIDDEN.getMessageKey(),
                        null,
                        LocaleContextHolder.getLocale()))
                .build()));
    }
}
