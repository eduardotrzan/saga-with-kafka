package com.order.payment.generic.controller.exception.http;

import org.springframework.http.HttpStatus;

import com.order.payment.generic.controller.exception.error.GenericHttpErrorCode;

public class UnprocessableError extends BaseHttpException {

    private static final GenericHttpErrorCode DEFAULT_ERROR = GenericHttpErrorCode.API_UNPROCESSABLE;

    public UnprocessableError() {
        super(DEFAULT_ERROR.getDescription());
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
