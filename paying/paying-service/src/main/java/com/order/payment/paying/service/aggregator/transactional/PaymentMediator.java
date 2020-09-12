package com.order.payment.paying.service.aggregator.transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.paying.dto.response.PaymentDto;
import com.order.payment.paying.service.business.PaymentService;
import com.order.payment.paying.service.mapper.PaymentMapper;

@RequiredArgsConstructor
@Component
public class PaymentMediator {

    private final PaymentService paymentService;

    private final PaymentMapper paymentMapper;

    @Transactional(readOnly = true)
    public Optional<PaymentDto> findByUuid(UUID paymentUuid) {
        return this.paymentService
                .findByUuid(paymentUuid)
                .flatMap(paymentMapper::toDto);
    }
}
