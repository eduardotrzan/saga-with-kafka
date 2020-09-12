package com.order.payment.paying.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.payment.paying.dto.response.PaymentDto;
import com.order.payment.paying.service.aggregator.transactional.PaymentMediator;
import com.order.payment.paying.service.validation.PaymentErrorCode;

@Api(value = "Payments Management")
@RestController
@RequestMapping({ "/v1/payments" })
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentMediator paymentMediator;

    @ApiOperation(value = "Retrieves a payment by its UUID.", response = PaymentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieves a payment."),
            @ApiResponse(code = 401, message = "You are not authorized to view the payment."),
            @ApiResponse(code = 404, message = "The payment you were trying to reach is NOT FOUND.")
    })
    @GetMapping(value = "/{paymentUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaymentDto findOrderByUuid(@PathVariable(value = "paymentUuid") UUID paymentUuid) {
        return this.paymentMediator.findByUuid(paymentUuid)
                .orElseThrow(PaymentErrorCode.PAYMENT_NOT_FOUND::buildError);
    }
}
