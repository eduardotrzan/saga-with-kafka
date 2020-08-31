package com.order.payment.generic.controller.exception.http;

import org.springframework.http.HttpStatus;

import com.order.payment.generic.controller.exception.error.GenericHttpErrorCode;

public class UnauthorizedError extends BaseHttpException {

    private static final GenericHttpErrorCode DEFAULT_ERROR = GenericHttpErrorCode.API_UNAUTHORIZED;

    public UnauthorizedError() {
        super(DEFAULT_ERROR.getDescription());
    }

    @Override
    protected HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
