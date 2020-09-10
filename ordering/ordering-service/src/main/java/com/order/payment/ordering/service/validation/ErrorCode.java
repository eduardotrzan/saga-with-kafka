package com.order.payment.ordering.service.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.order.payment.generic.exceptions.IAppError;

@Getter
@RequiredArgsConstructor
public enum ErrorCode implements IAppError {

    ORDER_CANNOT_CREATE("Ordering cannot be created.{}"),
    ORDER_NOT_FOUND("Ordering not found."),
    MAPPER_ERROR("Could not map {} to valid type of {}.");

    private final String description;
}
