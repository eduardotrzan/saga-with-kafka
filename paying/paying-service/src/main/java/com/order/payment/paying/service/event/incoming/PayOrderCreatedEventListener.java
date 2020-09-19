package com.order.payment.paying.service.event.incoming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.annotation.GenericKafkaListener;
import com.order.payment.paying.dto.request.PaymentCreateDto;
import com.order.payment.paying.service.aggregator.transactional.PaymentMediator;

@Slf4j
@RequiredArgsConstructor
@Component
public class PayOrderCreatedEventListener {

    private final PaymentMediator paymentMediator;

    @GenericKafkaListener(topic = PayOrderCreatedEvent.TOPIC,
                          consumerGroupId = PayOrderCreatedEvent.CONSUMER_GROUP_ID,
                          consumerName = PayOrderCreatedEvent.CONSUMER_NAME)
    public void handle(PayOrderCreatedEvent event) {
        log.info("Processing event={}.", event);
        String description = String.format("Payment of order with uuid=%s with amount=%s.",
                                           event.getUuid(), event.getAmount());
        PaymentCreateDto create = PaymentCreateDto.builder()
                .description(description)
                .amount(event.getAmount())
                .orderUuid(event.getUuid())
                .build();
        this.paymentMediator.payOrder(create);
    }

}
