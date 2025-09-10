package com.nisum.technical.exercise.domain.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.warn("Validación fallida: {}", ex.getMessage());
        return buildApiErrorResponse(EnumError.INVALID_ARGS);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatusCode status,
                                                                          WebRequest request) {
        log.warn("Error en la solicitud: {}", ex.getMessage());
        return buildApiErrorResponse(EnumError.INVALID_ARGS);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        log.warn("Cuerpo de la solicitud no válido: {}", ex.getMessage());
        return buildApiErrorResponse(EnumError.INVALID_BODY);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn("Violación de integridad de datos: {}", ex.getMessage());
        return buildApiErrorResponse(EnumError.DUPLICATE_EMAIL);
    }

    @ExceptionHandler(SimpleException.class)
    public ResponseEntity<Object> handleSimpleException(SimpleException ex) {
        log.warn("Error controlado [{}]: {}", ex.getCode(), ex.getMessage());
        return buildApiErrorResponse(ex.getErrorEnum());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        log.error("Error inesperado capturado", ex);
        return buildApiErrorResponse(EnumError.DEFAULT);
    }

    // ================= Private Helpers =================

    private ResponseEntity<Object> buildApiErrorResponse(EnumError error) {
        String mensaje = messageSource.getMessage(error.getMessageKey(), null, LocaleContextHolder.getLocale());
        ApiErrorResponse response = ApiErrorResponse.builder()
                .mensaje(mensaje)
                .build();
        return ResponseEntity.status(getHttpStatusForError(error)).body(response);
    }

    private HttpStatus getHttpStatusForError(EnumError error) {
        return switch (error) {
            case DUPLICATE_EMAIL -> HttpStatus.CONFLICT;
            case INVALID_ARGS, INVALID_BODY -> HttpStatus.BAD_REQUEST;
            case INVALID_CREDENTIALS, UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case NO_RESOURCE_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
