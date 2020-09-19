package com.order.payment.ordering.service.event.incoming;

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
@GenericKafkaEvent(topic = OrderPaymentFailureEvent.TOPIC)
public class OrderPaymentFailureEvent {

    public static final String TOPIC = "payment.failure.event";
    static final String CONSUMER_GROUP_ID = "OrderPaymentFailureEvent";
    static final String CONSUMER_NAME = "OrderPaymentFailureEventConsumer";

    private UUID uuid;
    private UUID orderUuid;
}
