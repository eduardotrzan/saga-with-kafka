package com.order.payment.generic.controller.exception.advisor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.order.payment.generic.controller.dto.ErrorDto;
import com.order.payment.generic.controller.exception.error.ErrorLevel;
import com.order.payment.generic.controller.exception.http.BadRequestError;
import com.order.payment.generic.controller.exception.http.BaseHttpException;
import com.order.payment.generic.controller.exception.http.InternalServerError;
import com.order.payment.generic.controller.exception.http.NotFoundError;
import com.order.payment.generic.controller.exception.http.UnauthorizedError;
import com.order.payment.generic.controller.mapper.ErrorMapper;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ExceptionControllerAdvisor {

    private final ErrorMapper errorMapper;

    @ExceptionHandler({ BadRequestError.class, MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Order(value = 0)
    public ErrorDto badRequest(Exception ex) {
        return buildErrorResponse(ex);
    }

    @ExceptionHandler({ UnauthorizedError.class, AccessDeniedException.class })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @Order(value = 1)
    public ErrorDto unAuthorizedHandler(Exception ex) {
        return buildErrorResponse(ex);
    }

    @ExceptionHandler({ NotFoundError.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @Order(value = 2)
    public ErrorDto notFoundHandler(Exception ex) {
        return buildErrorResponse(ex);
    }

    @ExceptionHandler({ InternalServerError.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Order(value = 3)
    public ErrorDto internalServerErrorHandler(Exception ex) {
        return buildErrorResponse(ex);
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @Order
    public ErrorDto defaultHandler(Exception ex) {
        return buildErrorResponse(ex);
    }

    private ErrorDto buildErrorResponse(Exception ex) {
        try {
            ErrorLevel errorLevel = ErrorLevel.ERROR;
            ErrorDto errorResponse;
            if (ex == null) {
                errorResponse = this.errorMapper.buildErrorForNullException();
            } else if (ex instanceof BaseHttpException) {
                BaseHttpException baseHttpException = (BaseHttpException) ex;
                errorLevel = baseHttpException.getErrorLevel();
                errorResponse = this.errorMapper.buildErrorForBaseHttpException(baseHttpException);
            } else if (ex instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException payloadConstraintException = (MethodArgumentNotValidException) ex;
                errorLevel = ErrorLevel.WARN;
                errorResponse = this.errorMapper.buildErrorForPayloadConstraintException(payloadConstraintException);
            } else {
                errorResponse = this.errorMapper.buildErrorForAnyOtherExceptionType(ex);
            }

            log(errorLevel, errorResponse, ex);
            return errorResponse;
        } catch (Exception e) {
            ErrorDto errorResponse = this.errorMapper.buildErrorForAnyOtherExceptionType(e);
            log(ErrorLevel.ERROR, errorResponse, e);
            return errorResponse;
        }
    }

    private void log(ErrorLevel errorLevel, ErrorDto errorResponse, Exception ex) {
        String error = String.format("An error has happened, built error response as errorResponse=%s.", errorResponse);

        switch (errorLevel) {
            case INFO:
                log.info(error, ex);
                break;
            case WARN:
                log.warn(error, ex);
                break;
            case ERROR:
            default:
                log.error(error, ex);
                break;
        }
    }
}
