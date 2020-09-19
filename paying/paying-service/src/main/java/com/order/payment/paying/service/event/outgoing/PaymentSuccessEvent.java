package com.order.payment.paying.service.event.outgoing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

import com.order.payment.generic.kafka.annotation.GenericKafkaEvent;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "uuid", "amount", "orderUuid" })
@GenericKafkaEvent(topic = PaymentSuccessEvent.TOPIC,
                   producerName = PaymentSuccessEvent.PRODUCER_NAME,
                   templateName = PaymentSuccessEvent.TEMPLATE_NAME)
public class PaymentSuccessEvent {

    public static final String TOPIC = "payment.success.event";
    public static final String PRODUCER_NAME = "PaymentSuccessEventProducer";
    public static final String TEMPLATE_NAME = "PaymentSuccessEventTemplate";

    private UUID uuid;
    private BigDecimal amount;
    private UUID orderUuid;
}
