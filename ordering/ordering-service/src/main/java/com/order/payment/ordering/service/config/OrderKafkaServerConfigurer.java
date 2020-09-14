package com.order.payment.ordering.service.config;

import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.KafkaAutoConfigurer;
import com.order.payment.ordering.service.event.OrderCreatedEvent;

@Component
public class OrderKafkaServerConfigurer {

    public OrderKafkaServerConfigurer(KafkaAutoConfigurer kafkaAutoConfigurer) {
        kafkaAutoConfigurer.createTopic(OrderCreatedEvent.class);

        kafkaAutoConfigurer.configureProducer(OrderCreatedEvent.class);

        kafkaAutoConfigurer.createTemplate(OrderCreatedEvent.class);
    }
}
