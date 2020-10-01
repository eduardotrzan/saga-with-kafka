package com.order.payment.orch.service.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.order.payment.orch.dto.request.OrderCreateDto;
import com.order.payment.orch.dto.response.OrderDto;
import com.order.payment.orch.service.mapper.OrderMapper;
import com.order.payment.ordering.api.OrdersManagementApi;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrdersManagementApi ordersManagementApi;

    private final OrderMapper orderMapper;

    public OrderDto create(OrderCreateDto orderCreate) {
        com.order.payment.ordering.dto.OrderCreateDto serviceRequestDto = orderMapper.toServiceRequestDto(orderCreate);
        com.order.payment.ordering.dto.OrderDto serviceDto = ordersManagementApi.create(serviceRequestDto);
        return orderMapper.toDto(serviceDto);
    }

    public OrderDto findByUuid(UUID orderUuid) {
        com.order.payment.ordering.dto.OrderDto serviceDto = ordersManagementApi.findOrderByUuid(orderUuid);
        return orderMapper.toDto(serviceDto);
    }

}
