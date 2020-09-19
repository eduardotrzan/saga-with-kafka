package com.order.payment.ordering.service.event.incoming;

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
@GenericKafkaEvent(topic = OrderPayedSuccessEvent.TOPIC)
public class OrderPayedSuccessEvent {

    public static final String TOPIC = "payment.success.event";
    static final String CONSUMER_GROUP_ID = "OrderPayedSuccessEvent";
    static final String CONSUMER_NAME = "OrderPayedSuccessEventConsumer";

    private UUID uuid;
    private BigDecimal amount;
    private UUID orderUuid;
}
