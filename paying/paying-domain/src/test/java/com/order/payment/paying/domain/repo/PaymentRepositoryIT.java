package com.order.payment.paying.domain.repo;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;

import com.order.payment.paying.domain.entity.Payment;
import com.order.payment.paying.domain.testutils.AbstractPaymentDomainRepoIT;

@Slf4j
public class PaymentRepositoryIT extends AbstractPaymentDomainRepoIT {

    @Test
    public void savePayment() {
        Payment payment = super.getDatabasePrefillUtils()
                .withRandomPayment()
                .saveAndGet()
                .getPayment();
        log.info("payment={}", payment);
    }
}
