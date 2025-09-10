package com.nisum.technical.exercise.domain.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class RestExceptionHandlerTest {

    @Mock
    private org.springframework.context.MessageSource messageSource;

    @InjectMocks
    private RestExceptionHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        for (EnumError error : EnumError.values()) {
            when(messageSource.getMessage(eq(error.getMessageKey()), any(), any()))
                    .thenReturn(error.getMessageKey());
        }
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        when(messageSource.getMessage(EnumError.DUPLICATE_EMAIL.getMessageKey(), null, LocaleContextHolder.getLocale()))
                .thenReturn("error.duplicate");
        DataIntegrityViolationException ex = new DataIntegrityViolationException("Duplicate");
        ResponseEntity<Object> response = handler.handleDataIntegrityViolationException(ex);
        ApiErrorResponse body = (ApiErrorResponse) response.getBody();
        assertNotNull(body);
        assertEquals("error.duplicate", body.getMensaje());
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
    }

    @Test
    void testHandleSimpleException() {
        SimpleException ex = new SimpleException(EnumError.NO_RESOURCE_FOUND);
        ResponseEntity<Object> response = handler.handleSimpleException(ex);
        ApiErrorResponse body = (ApiErrorResponse) response.getBody();
        assertNotNull(body);
        assertEquals("error.no.resource", body.getMensaje());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testHandleGeneralException() {
        RuntimeException ex = new RuntimeException("Algo salió mal");
        ResponseEntity<Object> response = handler.handleGeneralException(ex);
        ApiErrorResponse body = (ApiErrorResponse) response.getBody();
        assertNotNull(body);
        assertEquals("error.default", body.getMensaje());
        assertEquals(500, response.getStatusCodeValue());
    }

    @Test
    void testHandleMethodArgumentNotValid() {
        MethodArgumentNotValidException ex = org.mockito.Mockito.mock(MethodArgumentNotValidException.class);
        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(ex, null, null, null);
        ApiErrorResponse body = (ApiErrorResponse) response.getBody();
        assertNotNull(body);
        assertEquals("error.invalid.args", body.getMensaje());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testHandleServletRequestBindingException() {
        ServletRequestBindingException ex = org.mockito.Mockito.mock(ServletRequestBindingException.class);
        ResponseEntity<Object> response = handler.handleServletRequestBindingException(ex, null, null, null);
        ApiErrorResponse body = (ApiErrorResponse) response.getBody();
        assertNotNull(body);
        assertEquals("error.invalid.args", body.getMensaje());
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testHandleHttpMessageNotReadable() {
        HttpMessageNotReadableException ex = org.mockito.Mockito.mock(HttpMessageNotReadableException.class);
        ResponseEntity<Object> response = handler.handleHttpMessageNotReadable(ex, null, null, null);
        ApiErrorResponse body = (ApiErrorResponse) response.getBody();
        assertNotNull(body);
        assertEquals("error.invalid.body", body.getMensaje());
        assertEquals(400, response.getStatusCodeValue());
    }
}
