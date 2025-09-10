package com.nisum.technical.exercise.domain.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
public class SimpleException extends RuntimeException {

    private final int status;
    private final String code;
    private final String messageKey;
    private final EnumError errorEnum;

    public SimpleException(final EnumError enumError) {
        this(enumError, 500, null);
    }

    public SimpleException(final EnumError enumError, final Throwable cause) {
        this(enumError, 500, cause);
    }

    public SimpleException(final EnumError enumError, final int httpStatus) {
        this(enumError, httpStatus, null);
    }

    public SimpleException(final EnumError enumError, final int httpStatus, final Throwable cause) {
        super(enumError.getMessageKey(), cause);
        this.status = httpStatus;
        this.code = enumError.getCode();
        this.messageKey = enumError.getMessageKey();
        this.errorEnum = enumError;
    }
}
