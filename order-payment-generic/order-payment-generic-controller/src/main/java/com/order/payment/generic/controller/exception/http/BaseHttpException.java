package com.order.payment.generic.controller.exception.http;

import lombok.Getter;
import lombok.ToString;

import org.springframework.http.HttpStatus;

import com.order.payment.generic.controller.exception.error.ErrorLevel;

@Getter
@ToString(of = { "httpStatus", "errorLevel", "description", "details" })
public abstract class BaseHttpException extends RuntimeException {

    private static final long serialVersionUID = 7594975012021334502L;

    private final HttpStatus httpStatus;

    private ErrorLevel errorLevel = ErrorLevel.ERROR;

    private String description;

    private String details;

    protected BaseHttpException(String description) {
        this.httpStatus = this.getHttpStatus();
        this.description = description;
        this.details = null;
    }

    @Override
    public String getMessage() {
        return this.getDescription();
    }

    public <T extends BaseHttpException> T withErrorLevel(ErrorLevel errorLevel) {
        this.errorLevel = errorLevel;
        return (T) this;
    }

    public <T extends BaseHttpException> T withDescription(String description) {
        this.description = description;
        return (T) this;
    }

    public <T extends BaseHttpException> T withDetails(String details) {
        this.details = details;
        return (T) this;
    }

    protected abstract HttpStatus getHttpStatus();
}
