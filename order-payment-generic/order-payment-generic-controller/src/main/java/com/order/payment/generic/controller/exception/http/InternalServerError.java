package com.order.payment.generic.controller.exception.http;

import org.springframework.http.HttpStatus;

import com.order.payment.generic.controller.exception.error.GenericHttpErrorCode;

public class InternalServerError extends BaseHttpException {

    private static final GenericHttpErrorCode DEFAULT_ERROR = GenericHttpErrorCode.GENERAL;

    public InternalServerError() {
        super(DEFAULT_ERROR.getDescription());
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
