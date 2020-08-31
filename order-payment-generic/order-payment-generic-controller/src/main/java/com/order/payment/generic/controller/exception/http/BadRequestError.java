package com.order.payment.generic.controller.exception.http;

import org.springframework.http.HttpStatus;

import com.order.payment.generic.controller.exception.error.GenericHttpErrorCode;

public class BadRequestError extends BaseHttpException {

    private static final GenericHttpErrorCode DEFAULT_ERROR = GenericHttpErrorCode.API_BAD_REQUEST;

    public BadRequestError() {
        super(DEFAULT_ERROR.getDescription());
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
