package com.order.payment.generic.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.order.payment.generic.controller.dto.ErrorDto;
import com.order.payment.generic.controller.exception.http.BaseHttpException;

@Component
public class ErrorMapper {

    public ErrorDto buildErrorForAnyOtherExceptionType(Exception ex) {
        String description = String.format("Unexpected server error of %s.", ex.getClass()
                .getSimpleName());
        return ErrorDto.builder()
                .description(description)
                .build();
    }

    public ErrorDto buildErrorForNullException() {
        String description = "Unexpected server error.";
        return ErrorDto.builder()
                .description(description)
                .build();
    }

    public ErrorDto buildErrorForBaseHttpException(BaseHttpException baseHttpException) {
        return ErrorDto.builder()
                .description(baseHttpException.getDescription())
                .details(baseHttpException.getDetails())
                .build();
    }

    public ErrorDto buildErrorForPayloadConstraintException(
            MethodArgumentNotValidException payloadConstraintException) {
        return ErrorDto.builder()
                .description(getValidationMessage(payloadConstraintException))
                .build();
    }

    private String getValidationMessage(MethodArgumentNotValidException payloadConstraintException) {
        final String errorFormat = "[Field: %s, Error: %s]";
        BindingResult bindingResult = payloadConstraintException.getBindingResult();

        String fieldsMsg = bindingResult.getFieldErrors()
                .stream()
                .map(f -> String.format(errorFormat, f.getField(), f.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        return String.format("Invalid request field for object: %s, fields: %s.", bindingResult.getObjectName(),
                             fieldsMsg);
    }

}
