package com.order.payment.ordering.domain.testutils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.company.Company;
import com.devskiller.jfairy.producer.person.Person;
import com.order.payment.ordering.domain.entity.Order;
import com.order.payment.ordering.domain.entity.enums.OrderStatus;
import com.order.payment.ordering.domain.repo.OrderRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderDatabasePrefillUtils {

    private final OrderRepository orderRepository;

    private Order order;

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabasePrefillContext {

        private final Order order;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DatabasePrefillContext saveAndGet() {
        doSave();
        DatabasePrefillContext databasePrefillContext = DatabasePrefillContext.builder()
                .order(this.order)
                .build();
        flush();
        return databasePrefillContext;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save() {
        doSave();
        flush();
    }

    private void doSave() {
        this.saveOrder();
    }

    private void flush() {
        this.order = null;
    }

    private void saveOrder() {
        log.info("Saving order...");
        if (this.order == null) {
            log.info("order is null, skipping save.");
            return;
        }
        this.order = this.orderRepository.save(this.order);
        log.info("Saved order={}.", this.order);
    }

    public OrderDatabasePrefillUtils withRandomOrder() {
        Company company = Fairy.create().company();
        this.order = Order.builder()
                .status(OrderStatus.PENDING)
                .description(company.getName())
                .cost(randomDecimalBetween(1000, 10000))
                .build();
        return this;
    }

    private BigDecimal randomDecimalBetween(int minValue, int maxValue) {
        BigDecimal min = BigDecimal.valueOf(minValue);
        BigDecimal max = BigDecimal.valueOf(maxValue);

        BigDecimal randomFactor = BigDecimal.valueOf(Math.random());
        BigDecimal randomBigDecimal = min.add(randomFactor.multiply(max.subtract(min)));
        return randomBigDecimal.setScale(2, RoundingMode.HALF_DOWN);
    }
}
