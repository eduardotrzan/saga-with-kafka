package com.order.payment.ordering.service.aggregator.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.generic.kafka.KafkaTemplateManager;
import com.order.payment.ordering.domain.entity.Order;
import com.order.payment.ordering.dto.request.OrderCreateDto;
import com.order.payment.ordering.dto.response.OrderDto;
import com.order.payment.ordering.service.business.OrderService;
import com.order.payment.ordering.service.event.outgoing.OrderCreatedEvent;
import com.order.payment.ordering.service.mapper.OrderMapper;
import com.order.payment.ordering.service.validation.OrderErrorCode;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderMediator {

    private final OrderService orderService;
    private final KafkaTemplateManager kafkaTemplateManager;

    private final OrderMapper orderMapper;

    @Transactional
    public OrderDto create(OrderCreateDto request) {
        log.info("create request={}", request);
        Order toBeSaved = this.orderMapper.toNewEntity(request);
        Order saved = this.orderService.create(toBeSaved);
        OrderDto dto = this.orderMapper.toDto(saved)
                .orElseThrow(() -> OrderErrorCode.MAPPER_ERROR.buildError(
                        Order.class.getSimpleName(),
                        OrderDto.class.getSimpleName()
                ));

        this.publishOrderCreated(saved);
        return dto;
    }

    private void publishOrderCreated(Order order) {
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .uuid(order.getUuid())
                .amount(order.getCost())
                .build();
        this.kafkaTemplateManager
                .getKafkaTemplate(OrderCreatedEvent.class)
                .send(OrderCreatedEvent.TOPIC, event);
    }

    @Transactional(readOnly = true)
    public Optional<OrderDto> findByUuid(UUID orderUuid) {
        return this.orderService
                .findByUuid(orderUuid)
                .flatMap(orderMapper::toDto);
    }
}
