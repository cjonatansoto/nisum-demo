package com.nisum.technical.exercise.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Generated
public enum EnumError {
    DEFAULT("E_UNKNOWN_ERROR", "error.default"),
    INVALID_ARGS("E_INVALID_PARAMETERS", "error.invalid.args"),
    INVALID_BODY("E_INVALID_REQUEST_BODY", "error.invalid.body"),
    DUPLICATE_EMAIL("E_DUPLICATE_EMAIL", "error.duplicate"),
    INVALID_CREDENTIALS("E_INVALID_CREDENTIALS", "error.invalid.credentials"),
    NO_RESOURCE_FOUND("E_NO_RESOURCE_FOUND", "error.no.resource"),
    UNAUTHORIZED("E_UNAUTHORIZED", "error.unauthorized"),
    FORBIDDEN("E_FORBIDDEN","error.forbidden");
    private final String code;
    private final String messageKey;
}
