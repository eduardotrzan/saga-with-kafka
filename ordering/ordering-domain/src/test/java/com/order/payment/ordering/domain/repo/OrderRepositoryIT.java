package com.order.payment.ordering.domain.repo;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.order.payment.ordering.domain.entity.Order;
import com.order.payment.ordering.domain.testutils.AbstractOrderDomainRepoIT;

@Slf4j
public class OrderRepositoryIT extends AbstractOrderDomainRepoIT {

    @Test
    public void saveOrder() {
        Order order = super.getDatabasePrefillUtils()
                .withRandomOrder()
                .saveAndGet()
                .getOrder();
        log.info("order={}", order);
    }
}
