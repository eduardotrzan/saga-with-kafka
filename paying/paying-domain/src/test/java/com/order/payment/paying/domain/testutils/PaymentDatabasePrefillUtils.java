package com.order.payment.paying.domain.testutils;

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
import com.order.payment.paying.domain.entity.Payment;
import com.order.payment.paying.domain.entity.enums.PaymentStatus;
import com.order.payment.paying.domain.repo.PaymentRepository;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentDatabasePrefillUtils {

    private final PaymentRepository paymentRepository;

    private Payment payment;

    @Getter
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DatabasePrefillContext {

        private final Payment payment;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public DatabasePrefillContext saveAndGet() {
        doSave();
        DatabasePrefillContext databasePrefillContext = DatabasePrefillContext.builder()
                .payment(this.payment)
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
        this.savePayment();
    }

    private void flush() {
        this.payment = null;
    }

    private void savePayment() {
        log.info("Saving payment...");
        if (this.payment == null) {
            log.info("payment is null, skipping save.");
            return;
        }
        this.payment = this.paymentRepository.save(this.payment);
        log.info("payment order={}.", this.payment);
    }

    public PaymentDatabasePrefillUtils withRandomPayment() {
        Company company = Fairy.create().company();
        this.payment = Payment.builder()
                .status(PaymentStatus.PENDING)
                .description(company.getName())
                .amount(randomDecimalBetween(1000, 10000))
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
