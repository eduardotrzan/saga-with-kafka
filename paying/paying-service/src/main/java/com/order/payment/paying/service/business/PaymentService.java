package com.order.payment.paying.service.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.paying.domain.entity.Payment;
import com.order.payment.paying.domain.entity.enums.PaymentStatus;
import com.order.payment.paying.domain.repo.PaymentRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository repo;

    @Transactional(propagation = Propagation.MANDATORY)
    public Payment create(Payment order) {
        order.setStatus(PaymentStatus.PENDING);
        return this.repo.save(order);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Optional<Payment> findByUuid(UUID uuid) {
        return this.repo.findByUuid(uuid);
    }

}
