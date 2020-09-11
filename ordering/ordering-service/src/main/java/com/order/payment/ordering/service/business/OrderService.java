package com.order.payment.ordering.service.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.ordering.domain.entity.Order;
import com.order.payment.ordering.domain.entity.enums.OrderStatus;
import com.order.payment.ordering.domain.repo.OrderRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderRepository repo;

    @Transactional(propagation = Propagation.MANDATORY)
    public Order create(Order order) {
        order.setStatus(OrderStatus.PENDING);
        return this.repo.save(order);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<Order> findByUuid(UUID uuid) {
        return this.repo.findByUuid(uuid);
    }

}
