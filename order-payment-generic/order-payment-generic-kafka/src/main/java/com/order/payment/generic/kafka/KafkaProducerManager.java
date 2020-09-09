package com.order.payment.generic.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.annotation.GenericKafkaEvent;
import com.order.payment.generic.kafka.config.KafkaPropConfig;

@Slf4j
@RequiredArgsConstructor
@Component
class KafkaProducerManager {

    private final KafkaPropConfig kafkaPropConfig;
    private final ConfigurableListableBeanFactory beanFactory;

    <T> void configure(Class<T> clazz) {
        GenericKafkaEvent annotation = clazz.getAnnotation(GenericKafkaEvent.class);
        DefaultKafkaProducerFactory<String, T> container = this.createProducer(annotation);
        this.registerContainer(container, annotation);
    }

    private <T> DefaultKafkaProducerFactory<String, T> createProducer(GenericKafkaEvent annotation) {
        Map<String, Object> consumerProps = this.eventProducerProperties(annotation);
        return new DefaultKafkaProducerFactory<>(consumerProps);
    }

    private <T> void registerContainer(DefaultKafkaProducerFactory<String, T> producer, GenericKafkaEvent annotation) {
        String beanName = annotation.producerName();
        final Object singletonObject = beanFactory.applyBeanPostProcessorsAfterInitialization(producer, beanName);
        beanFactory.registerSingleton(beanName, singletonObject);
    }

    private Map<String, Object> eventProducerProperties(GenericKafkaEvent annotation) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropConfig.getBootstrapAddress());
        props.put(ProducerConfig.LINGER_MS_CONFIG, annotation.lingerMs());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, annotation.ackMode());
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        props.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, String.valueOf(annotation.enableIdempotence()));
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, annotation.retryBackoffMs());
        props.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, annotation.reconnectBackoffMs());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaPropConfig.getClientId());
        return props;
    }
}
