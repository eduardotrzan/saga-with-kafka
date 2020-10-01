package com.order.payment.orch.service.mapper;

import lombok.RequiredArgsConstructor;

import java.time.ZoneOffset;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.order.payment.orch.dto.enums.OrderStatusDto;
import com.order.payment.orch.dto.request.OrderCreateDto;
import com.order.payment.orch.dto.response.OrderDto;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    public com.order.payment.ordering.dto.OrderCreateDto toServiceRequestDto(OrderCreateDto request) {
        Objects.requireNonNull(request);

        return new com.order.payment.ordering.dto.OrderCreateDto()
                .description(request.getDescription())
                .cost(request.getCost());
    }

    public OrderDto toDto(com.order.payment.ordering.dto.OrderDto serviceDto) {
        Objects.requireNonNull(serviceDto);

        OrderStatusDto status = OrderStatusDto.valueOf(serviceDto.getStatus().name());

        return OrderDto.builder()
                .uuid(serviceDto.getUuid())
                .createDate(serviceDto.getCreateDate().atOffset(ZoneOffset.UTC))
                .updateDate(serviceDto.getUpdateDate().atOffset(ZoneOffset.UTC))
                .status(status)
                .description(serviceDto.getDescription())
                .cost(serviceDto.getCost())
                .build();
    }
}
