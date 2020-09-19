package com.order.payment.paying.service.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.order.payment.generic.exceptions.IAppError;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements IAppError {

    PAYMENT_CANNOT_CREATE("Payment cannot be created.{}"),
    PAYMENT_NOT_FOUND("Payment not found."),
    PAYMENT_INVALID_STATUS("Invalid payment status for update."),
    MAPPER_ERROR("Could not map {} to valid type of {}.");

    private final String description;
}
