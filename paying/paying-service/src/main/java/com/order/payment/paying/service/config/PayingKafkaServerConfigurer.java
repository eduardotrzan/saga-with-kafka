package com.order.payment.paying.service.config;

import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.KafkaAutoConfigurer;
import com.order.payment.paying.service.event.PayOrderCreatedEvent;
import com.order.payment.paying.service.event.PayOrderCreatedEventListener;

@Component
public class PayingKafkaServerConfigurer {

    public PayingKafkaServerConfigurer(KafkaAutoConfigurer kafkaAutoConfigurer) {
        kafkaAutoConfigurer.createTopic(PayOrderCreatedEvent.class);

        kafkaAutoConfigurer.configureConsumer(PayOrderCreatedEventListener.class, PayOrderCreatedEvent.class);
    }
}
