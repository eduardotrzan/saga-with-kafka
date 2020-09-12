package com.order.payment.ordering.service.aggregator.transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.ordering.domain.entity.Order;
import com.order.payment.ordering.dto.request.OrderCreateDto;
import com.order.payment.ordering.dto.response.OrderDto;
import com.order.payment.ordering.service.business.OrderService;
import com.order.payment.ordering.service.mapper.OrderMapper;
import com.order.payment.ordering.service.validation.OrderErrorCode;

@RequiredArgsConstructor
@Component
public class OrderMediator {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto create(OrderCreateDto request) {
        Order toBeSaved = this.orderMapper.toNewEntity(request);
        Order saved = this.orderService.create(toBeSaved);
        return this.orderMapper.toDto(saved)
                .orElseThrow(() -> OrderErrorCode.MAPPER_ERROR.buildError(
                        Order.class.getSimpleName(),
                        OrderDto.class.getSimpleName()
                ));
    }

    @Transactional(readOnly = true)
    public Optional<OrderDto> findByUuid(UUID orderUuid) {
        return this.orderService
                .findByUuid(orderUuid)
                .flatMap(orderMapper::toDto);
    }
}
