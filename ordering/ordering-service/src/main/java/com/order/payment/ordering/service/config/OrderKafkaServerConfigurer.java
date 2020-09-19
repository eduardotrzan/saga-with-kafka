package com.order.payment.ordering.service.config;

import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.KafkaAutoConfigurer;
import com.order.payment.ordering.service.event.incoming.OrderPayedSuccessEvent;
import com.order.payment.ordering.service.event.incoming.OrderPayedSuccessEventListener;
import com.order.payment.ordering.service.event.incoming.OrderPaymentFailureEvent;
import com.order.payment.ordering.service.event.incoming.OrderPaymentFailureEventListener;
import com.order.payment.ordering.service.event.outgoing.OrderCreatedEvent;

@Component
public class OrderKafkaServerConfigurer {

    public OrderKafkaServerConfigurer(KafkaAutoConfigurer kafkaAutoConfigurer) {
        configureIncomingEvents(kafkaAutoConfigurer);
        configureOutgoingEvents(kafkaAutoConfigurer);
    }

    private void configureIncomingEvents(KafkaAutoConfigurer kafkaAutoConfigurer) {
        kafkaAutoConfigurer.createTopic(OrderPayedSuccessEvent.class);
        kafkaAutoConfigurer.createTopic(OrderPaymentFailureEvent.class);

        kafkaAutoConfigurer.configureConsumer(OrderPayedSuccessEventListener.class, OrderPayedSuccessEvent.class);
        kafkaAutoConfigurer.configureConsumer(OrderPaymentFailureEventListener.class, OrderPaymentFailureEvent.class);
    }

    private void configureOutgoingEvents(KafkaAutoConfigurer kafkaAutoConfigurer) {
        kafkaAutoConfigurer.createTopic(OrderCreatedEvent.class);

        kafkaAutoConfigurer.configureProducer(OrderCreatedEvent.class);

        kafkaAutoConfigurer.createTemplate(OrderCreatedEvent.class);
    }
}
