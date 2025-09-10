package com.nisum.technical.exercise.infrastructure.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.technical.exercise.domain.exception.ApiErrorResponse;
import com.nisum.technical.exercise.domain.exception.EnumError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UnauthorizedHandlerTest {

    private MessageSource messageSource;
    private UnauthorizedHandler unauthorizedHandler;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private AuthenticationException exception;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        messageSource = mock(MessageSource.class);
        unauthorizedHandler = new UnauthorizedHandler(messageSource);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        exception = mock(AuthenticationException.class);

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        when(messageSource.getMessage(
                EnumError.UNAUTHORIZED.getMessageKey(),
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("No autorizado");
    }

    @Test
    void testCommenceUnauthorized() throws Exception {
        unauthorizedHandler.commence(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        verify(response).setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        ApiErrorResponse apiError = mapper.readValue(responseWriter.toString(), ApiErrorResponse.class);

        assertEquals("No autorizado", apiError.getMensaje());
    }
}
