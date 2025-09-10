package com.nisum.technical.exercise.infrastructure.security.handlers;

import com.nisum.technical.exercise.domain.exception.ApiErrorResponse;
import com.nisum.technical.exercise.domain.exception.EnumError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ForbiddenHandlerTest {

    private MessageSource messageSource;
    private ForbiddenHandler forbiddenHandler;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private AccessDeniedException exception;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        messageSource = mock(MessageSource.class);
        forbiddenHandler = new ForbiddenHandler(messageSource);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        exception = mock(AccessDeniedException.class);

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        when(messageSource.getMessage(
                EnumError.FORBIDDEN.getMessageKey(),
                null,
                LocaleContextHolder.getLocale()
        )).thenReturn("Acceso prohibido");
    }

    @Test
    void testHandleForbidden() throws Exception {
        forbiddenHandler.handle(request, response, exception);

        verify(response).setStatus(HttpServletResponse.SC_FORBIDDEN);

        verify(response).setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        ApiErrorResponse apiError = mapper.readValue(responseWriter.toString(), ApiErrorResponse.class);

        assertEquals("Acceso prohibido", apiError.getMensaje());
    }
}
