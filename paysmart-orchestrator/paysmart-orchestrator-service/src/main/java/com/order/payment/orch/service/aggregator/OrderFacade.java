package com.order.payment.orch.service.aggregator;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.order.payment.orch.dto.request.OrderCreateDto;
import com.order.payment.orch.dto.response.OrderDto;
import com.order.payment.orch.dto.response.PaymentDto;
import com.order.payment.orch.service.business.OrderService;
import com.order.payment.orch.service.business.PaymentService;

@RequiredArgsConstructor
@Component
public class OrderFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderDto create(OrderCreateDto request) {
        OrderDto order = this.orderService.create(request);
        return appendPaymentsToOrder(order);
    }

    public OrderDto findByUuid(UUID orderUuid) {
        OrderDto order = this.orderService.findByUuid(orderUuid);
        return appendPaymentsToOrder(order);
    }

    private OrderDto appendPaymentsToOrder(OrderDto order) {
        List<PaymentDto> payments = this.paymentService.findByOrderUuid(order.getUuid());
        order.setPayments(payments);
        return order;
    }
}
