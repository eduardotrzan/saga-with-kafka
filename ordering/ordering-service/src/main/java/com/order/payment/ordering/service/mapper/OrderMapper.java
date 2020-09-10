package com.order.payment.ordering.service.mapper;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.ordering.domain.entity.Order;
import com.order.payment.ordering.dto.enums.OrderStatusDto;
import com.order.payment.ordering.dto.request.OrderCreateDto;
import com.order.payment.ordering.dto.response.OrderDto;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    public Order toNewEntity(OrderCreateDto request) {
        Objects.requireNonNull(request);

        return Order.builder()
                .description(request.getDescription())
                .cost(request.getCost())
                .build();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<OrderDto> toDto(Order entity) {
        if (entity == null) {
            return Optional.empty();
        }

        OrderDto dto = buildDto(entity);
        return Optional.of(dto);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<OrderDto> toDtos(List<Order> entities) {
        return Objects.requireNonNullElse(entities, Collections.<Order>emptyList())
                .stream()
                .map(this::buildDto)
                .collect(Collectors.toList());
    }

    private OrderDto buildDto(Order entity) {
        OrderStatusDto status = OrderStatusDto.valueOf(entity.getStatus().name());

        return OrderDto.builder()
                .uuid(entity.getUuid())
                .createDate(entity.getCreateDate())
                .updateDate(entity.getUpdateDate())
                .status(status)
                .description(entity.getDescription())
                .cost(entity.getCost())
                .build();
    }
}
