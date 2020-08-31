package com.order.payment.generic.controller.exception.http;

import org.springframework.http.HttpStatus;

import com.order.payment.generic.controller.exception.error.GenericHttpErrorCode;

public class NotFoundError extends BaseHttpException {

    private static final GenericHttpErrorCode DEFAULT_ERROR = GenericHttpErrorCode.ENTITY_NOT_FOUND;

    public NotFoundError() {
        super(DEFAULT_ERROR.getDescription());
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
