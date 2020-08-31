package com.order.payment.generic.controller.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenericHttpErrorCode {
    GENERAL("An error has occurred."),
    ENTITY_NOT_FOUND("No entity was found."),
    API_UNPROCESSABLE("Request could not be processed."),
    API_BAD_REQUEST("Payload contains invalid information for the request."),
    API_UNAUTHORIZED("Access not authorized to the resource."),
    SERVER_DOWN("Server is down. Cannot reach.");

    private final String description;

}
