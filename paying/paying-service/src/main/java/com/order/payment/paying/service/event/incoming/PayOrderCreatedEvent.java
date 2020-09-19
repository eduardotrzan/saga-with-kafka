package com.order.payment.paying.service.event.incoming;

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
@ToString(of = { "uuid", "amount" })
@GenericKafkaEvent(topic = PayOrderCreatedEvent.TOPIC)
public class PayOrderCreatedEvent {

    static final String TOPIC = "order.created.event";
    static final String CONSUMER_GROUP_ID = "PayOrderCreatedEvent";
    static final String CONSUMER_NAME = "PayOrderCreatedEventConsumer";

    private UUID uuid;

    private BigDecimal amount;
}
