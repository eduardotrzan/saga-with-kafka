package com.order.payment.paying.service.config;

import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.KafkaAutoConfigurer;
import com.order.payment.paying.service.event.incoming.PayOrderCreatedEvent;
import com.order.payment.paying.service.event.incoming.PayOrderCreatedEventListener;
import com.order.payment.paying.service.event.outgoing.PaymentFailureEvent;
import com.order.payment.paying.service.event.outgoing.PaymentSuccessEvent;

@Component
public class PayingKafkaServerConfigurer {

    public PayingKafkaServerConfigurer(KafkaAutoConfigurer kafkaAutoConfigurer) {
        configureIncomingEvents(kafkaAutoConfigurer);
        configureOutgoingEvents(kafkaAutoConfigurer);
    }

    private void configureIncomingEvents(KafkaAutoConfigurer kafkaAutoConfigurer) {
        kafkaAutoConfigurer.createTopic(PayOrderCreatedEvent.class);

        kafkaAutoConfigurer.configureConsumer(PayOrderCreatedEventListener.class, PayOrderCreatedEvent.class);
    }

    private void configureOutgoingEvents(KafkaAutoConfigurer kafkaAutoConfigurer) {
        kafkaAutoConfigurer.createTopic(PaymentSuccessEvent.class);
        kafkaAutoConfigurer.createTopic(PaymentFailureEvent.class);

        kafkaAutoConfigurer.configureProducer(PaymentSuccessEvent.class);
        kafkaAutoConfigurer.configureProducer(PaymentFailureEvent.class);

        kafkaAutoConfigurer.createTemplate(PaymentSuccessEvent.class);
        kafkaAutoConfigurer.createTemplate(PaymentFailureEvent.class);
    }
}
