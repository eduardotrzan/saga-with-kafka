package com.order.payment.paying.service.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.paying.domain.entity.Payment;
import com.order.payment.paying.domain.entity.enums.PaymentStatus;
import com.order.payment.paying.domain.repo.PaymentRepository;
import com.order.payment.paying.service.validation.PaymentErrorCode;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository repo;

    @Transactional(propagation = Propagation.MANDATORY)
    public Payment create(Payment payment) {
        payment.setStatus(PaymentStatus.PENDING);
        return this.repo.save(payment);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<Payment> findByUuid(UUID uuid) {
        return this.repo.findByUuid(uuid);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Payment> findByOrderUuid(UUID orderUuid) {
        return this.repo.findByOrderUuid(orderUuid);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Payment update(Payment payment) {
        Payment originalPayment = this.repo.findByUuid(payment.getUuid())
                .orElseThrow(PaymentErrorCode.PAYMENT_NOT_FOUND::buildError);

        if (PaymentStatus.PENDING != originalPayment.getStatus()) {
            throw PaymentErrorCode.PAYMENT_INVALID_STATUS.buildError(originalPayment.getStatus());
        }

        originalPayment.setStatus(payment.getStatus());
        return this.repo.save(originalPayment);
    }

}
