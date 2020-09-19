package com.order.payment.ordering.service.event.outgoing;

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
@GenericKafkaEvent(topic = OrderCreatedEvent.TOPIC,
                   producerName = OrderCreatedEvent.PRODUCER_NAME,
                   templateName = OrderCreatedEvent.TEMPLATE_NAME)
public class OrderCreatedEvent {

    public static final String TOPIC = "order.created.event";
    public static final String PRODUCER_NAME = "OrderCreatedEventProducer";
    public static final String TEMPLATE_NAME = "OrderCreatedEventTemplate";

    private UUID uuid;

    private BigDecimal amount;
}
