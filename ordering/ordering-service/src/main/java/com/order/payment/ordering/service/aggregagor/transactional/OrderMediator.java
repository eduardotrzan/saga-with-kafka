package com.order.payment.ordering.service.aggregagor.transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.ordering.dto.response.OrderDto;
import com.order.payment.ordering.service.business.OrderService;
import com.order.payment.ordering.service.mapper.OrderMapper;

@RequiredArgsConstructor
@Component
public class OrderMediator {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public Optional<OrderDto> findByUuid(UUID orderUuid) {
        return this.orderService
                .findByUuid(orderUuid)
                .flatMap(orderMapper::toDto);
    }
}
