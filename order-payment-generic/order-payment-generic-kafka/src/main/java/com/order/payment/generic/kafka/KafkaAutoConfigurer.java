package com.order.payment.generic.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaAutoConfigurer {

    private final KafkaTopicManager kafkaTopicManager;
    private final KafkaProducerManager kafkaProducerManager;
    private final KafkaConsumerManager kafkaConsumerManager;
    private final KafkaTemplateManager kafkaTemplateManager;

    public void createTopic(Class<?>... eventClasses) {
        Stream.of(eventClasses).forEach(kafkaTopicManager::configure);
    }

    public void configureProducer(Class<?>... eventClasses) {
        Stream.of(eventClasses).forEach(kafkaProducerManager::configure);
    }

    public void configureConsumer(Class<?> beanClass, Class<?> eventClass) {
        kafkaConsumerManager.configure(beanClass, eventClass);
    }

    public void createTemplate(Class<?>... eventClasses) {
        Stream.of(eventClasses).forEach(kafkaTemplateManager::configure);
    }
}