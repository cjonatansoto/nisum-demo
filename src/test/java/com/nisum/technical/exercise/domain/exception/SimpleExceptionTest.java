package com.nisum.technical.exercise.domain.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleExceptionTest {

    @Test
    void testConstructorWithEnumOnly() {
        SimpleException ex = new SimpleException(EnumError.NO_RESOURCE_FOUND);
        assertEquals(500, ex.getStatus());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getCode(), ex.getCode());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessageKey());
        assertEquals(EnumError.NO_RESOURCE_FOUND, ex.getErrorEnum());
        assertNull(ex.getCause());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessage());
    }

    @Test
    void testConstructorWithEnumAndHttpStatus() {
        SimpleException ex = new SimpleException(EnumError.NO_RESOURCE_FOUND, 404);
        assertEquals(404, ex.getStatus());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getCode(), ex.getCode());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessageKey());
        assertEquals(EnumError.NO_RESOURCE_FOUND, ex.getErrorEnum());
        assertNull(ex.getCause());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessage());
    }

    @Test
    void testConstructorWithEnumAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        SimpleException ex = new SimpleException(EnumError.NO_RESOURCE_FOUND, cause);
        assertEquals(500, ex.getStatus());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getCode(), ex.getCode());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessageKey());
        assertEquals(EnumError.NO_RESOURCE_FOUND, ex.getErrorEnum());
        assertEquals(cause, ex.getCause());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessage());
    }

    @Test
    void testConstructorWithEnumHttpStatusAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        SimpleException ex = new SimpleException(EnumError.NO_RESOURCE_FOUND, 404, cause);
        assertEquals(404, ex.getStatus());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getCode(), ex.getCode());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessageKey());
        assertEquals(EnumError.NO_RESOURCE_FOUND, ex.getErrorEnum());
        assertEquals(cause, ex.getCause());
        assertEquals(EnumError.NO_RESOURCE_FOUND.getMessageKey(), ex.getMessage());
    }
}
