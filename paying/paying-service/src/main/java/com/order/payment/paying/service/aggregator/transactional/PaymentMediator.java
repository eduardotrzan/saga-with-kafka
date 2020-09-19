package com.order.payment.paying.service.aggregator.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.generic.kafka.KafkaTemplateManager;
import com.order.payment.paying.domain.entity.Payment;
import com.order.payment.paying.domain.entity.enums.PaymentStatus;
import com.order.payment.paying.dto.request.PaymentCreateDto;
import com.order.payment.paying.dto.response.PaymentDto;
import com.order.payment.paying.service.business.PaymentService;
import com.order.payment.paying.service.business.ProcessingState;
import com.order.payment.paying.service.event.outgoing.PaymentFailureEvent;
import com.order.payment.paying.service.event.outgoing.PaymentSuccessEvent;
import com.order.payment.paying.service.mapper.PaymentMapper;
import com.order.payment.paying.service.validation.PaymentErrorCode;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentMediator {

    private final PaymentService paymentService;
    private final ProcessingState processingState;

    private final KafkaTemplateManager kafkaTemplateManager;

    private final PaymentMapper paymentMapper;

    @Transactional(readOnly = true)
    public Optional<PaymentDto> findByUuid(UUID paymentUuid) {
        return this.paymentService
                .findByUuid(paymentUuid)
                .flatMap(paymentMapper::toDto);
    }

    @Transactional
    public PaymentDto payOrder(PaymentCreateDto request) {
        Payment toBeSaved = this.paymentMapper.toNewEntity(request);

        Payment orderPayment = this.paymentService.create(toBeSaved);

        try {
            Payment toUpdate = Payment.builder()
                    .uuid(orderPayment.getUuid())
                    .status(this.deriveStatus())
                    .build();
            orderPayment = this.paymentService.update(toUpdate);
            this.publishOrderCreated(orderPayment);
        } catch (InterruptedException e) {
            log.error("Failed processing payment.", e);
            throw PaymentErrorCode.PAYMENT_CANNOT_CREATE.buildError(request);
        }

        return this.paymentMapper.toDto(orderPayment)
                .orElseThrow(() -> PaymentErrorCode.MAPPER_ERROR.buildError(
                        Payment.class.getSimpleName(),
                        PaymentDto.class.getSimpleName()
                ));
    }

    private PaymentStatus deriveStatus() throws InterruptedException {
        if (ProcessingState.ProcessingStateStatus.SUCCESS == processingState.getProcessingStateStatus()) {
            return PaymentStatus.DONE;
        }

        if (ProcessingState.ProcessingStateStatus.FAIL == processingState.getProcessingStateStatus()) {
            return PaymentStatus.FAILED;
        }

        if (ProcessingState.ProcessingStateStatus.DELAY_FAIL == processingState.getProcessingStateStatus()) {
            Thread.sleep(10000); // 10s
            return PaymentStatus.FAILED;
        }

        if (ProcessingState.ProcessingStateStatus.DELAY_SUCCESS == processingState.getProcessingStateStatus()) {
            Thread.sleep(10000); // 10s
            return PaymentStatus.DONE;
        }

        return PaymentStatus.PENDING;
    }

    private void publishOrderCreated(Payment payment) {
        if (PaymentStatus.DONE == payment.getStatus()) {
            publishSuccess(payment);
        }

        if (PaymentStatus.FAILED == payment.getStatus()) {
            publishFailure(payment);
        }
    }

    private void publishSuccess(Payment payment) {
        PaymentSuccessEvent event = PaymentSuccessEvent.builder()
                .uuid(payment.getUuid())
                .amount(payment.getAmount())
                .orderUuid(payment.getOrderUuid())
                .build();
        this.kafkaTemplateManager
                .getKafkaTemplate(PaymentSuccessEvent.class)
                .send(PaymentSuccessEvent.TOPIC, event);
    }

    private void publishFailure(Payment payment) {
        PaymentFailureEvent event = PaymentFailureEvent.builder()
                .uuid(payment.getUuid())
                .orderUuid(payment.getOrderUuid())
                .build();
        this.kafkaTemplateManager
                .getKafkaTemplate(PaymentFailureEvent.class)
                .send(PaymentFailureEvent.TOPIC, event);
    }
}
