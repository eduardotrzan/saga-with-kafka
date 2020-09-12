package com.order.payment.ordering.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import javax.annotation.security.PermitAll;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.order.payment.ordering.dto.request.OrderCreateDto;
import com.order.payment.ordering.dto.response.OrderDto;
import com.order.payment.ordering.service.aggregator.transactional.OrderMediator;
import com.order.payment.ordering.service.validation.OrderErrorCode;

@Api(value = "Orders Management")
@RestController
@RequestMapping({ "/v1/orders" })
@RequiredArgsConstructor
public class OrderController {

    private final OrderMediator orderMediator;

    @ApiOperation(value = "Creates a new order.", response = OrderDto.class)
    @ApiResponse(code = 201, message = "Successfully create an order.")
    @PermitAll
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@Validated @RequestBody OrderCreateDto request) {
        return this.orderMediator.create(request);
    }

    @ApiOperation(value = "Retrieves an order by its UUID.", response = OrderDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieves an order."),
            @ApiResponse(code = 401, message = "You are not authorized to view the order."),
            @ApiResponse(code = 404, message = "The order you were trying to reach is NOT FOUND.")
    })
    @GetMapping(value = "/{orderUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto findOrderByUuid(@PathVariable(value = "orderUuid") UUID orderUuid) {
        return this.orderMediator.findByUuid(orderUuid)
                .orElseThrow(OrderErrorCode.ORDER_NOT_FOUND::buildError);
    }
}
