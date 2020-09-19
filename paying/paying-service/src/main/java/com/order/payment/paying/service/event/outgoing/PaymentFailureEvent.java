package com.order.payment.paying.service.event.outgoing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

import com.order.payment.generic.kafka.annotation.GenericKafkaEvent;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = { "uuid", "orderUuid" })
@GenericKafkaEvent(topic = PaymentFailureEvent.TOPIC,
                   producerName = PaymentFailureEvent.PRODUCER_NAME,
                   templateName = PaymentFailureEvent.TEMPLATE_NAME)
public class PaymentFailureEvent {

    public static final String TOPIC = "payment.failure.event";
    public static final String PRODUCER_NAME = "PaymentFailureEventProducer";
    public static final String TEMPLATE_NAME = "PaymentFailureEventTemplate";

    private UUID uuid;
    private UUID orderUuid;
}
