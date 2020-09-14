package com.order.payment.paying.service.aggregator.transactional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.paying.domain.entity.Payment;
import com.order.payment.paying.dto.request.PaymentCreateDto;
import com.order.payment.paying.dto.response.PaymentDto;
import com.order.payment.paying.service.business.PaymentService;
import com.order.payment.paying.service.business.ProcessingState;
import com.order.payment.paying.service.mapper.PaymentMapper;
import com.order.payment.paying.service.validation.PaymentErrorCode;

@RequiredArgsConstructor
@Component
public class PaymentMediator {

    private final PaymentService paymentService;
    private final ProcessingState processingState;

    private final PaymentMapper paymentMapper;

    @Transactional(readOnly = true)
    public Optional<PaymentDto> findByUuid(UUID paymentUuid) {
        return this.paymentService
                .findByUuid(paymentUuid)
                .flatMap(paymentMapper::toDto);
    }

    @Transactional
    public PaymentDto payOrder(PaymentCreateDto request) {
        if (ProcessingState.ProcessingStateStatus.FAIL == processingState.getProcessingStateStatus()) {
            throw PaymentErrorCode.PAYMENT_CANNOT_CREATE.buildError(request);
        }

        Payment toBeSaved = this.paymentMapper.toNewEntity(request);

        Payment saved = this.paymentService.create(toBeSaved);
        PaymentDto dto = this.paymentMapper.toDto(saved)
                .orElseThrow(() -> PaymentErrorCode.MAPPER_ERROR.buildError(
                        Payment.class.getSimpleName(),
                        PaymentDto.class.getSimpleName()
                ));

        // add new event to inform order.
        return dto;
    }
}
