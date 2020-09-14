package com.order.payment.generic.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.MethodIntrospector;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order.payment.generic.kafka.annotation.GenericKafkaListener;
import com.order.payment.generic.kafka.config.KafkaPropConfig;
import com.order.payment.generic.utils.mapper.GenericObjectMapper;

@Slf4j
@RequiredArgsConstructor
@Component
class KafkaConsumerManager {

    private final KafkaPropConfig kafkaPropConfig;
    private final ConfigurableListableBeanFactory beanFactory;
    private final KafkaMessageListenerFactory kafkaMessageListenerFactory;

    <T> void configure(Class<?> beanClass, Class<T> eventClass) {
        MethodIntrospector
                .selectMethods(beanClass, (Method method) -> method.isAnnotationPresent(GenericKafkaListener.class))
                .forEach(m -> initializeEventListener(beanClass, eventClass, m));
    }

    private <T> void initializeEventListener(Class<?> beanClass, Class<T> eventClass, Method method) {
        GenericKafkaListener annotation = method.getAnnotation(GenericKafkaListener.class);
        ConcurrentMessageListenerContainer<String, T> container = this.createConsumer(eventClass, annotation);
        addListener(container, beanClass, eventClass, method);
        this.registerContainer(container);
    }

    private <T> ConcurrentMessageListenerContainer<String, T> createConsumer(Class<T> eventClass, GenericKafkaListener annotation) {
        Map<String, Object> consumerProps = consumerProperties(eventClass, annotation, this.kafkaPropConfig);
        JsonDeserializer<T> jsonDeserializer = customizedJsonDeserializer(eventClass);
        var consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps,
                                                                new StringDeserializer(),
                                                                jsonDeserializer);

        ContainerProperties containerProps = new ContainerProperties(annotation.topic());
        containerProps.setAckMode(ContainerProperties.AckMode.RECORD);
        containerProps.setMissingTopicsFatal(false);

        var container = new ConcurrentMessageListenerContainer<>(consumerFactory, containerProps);
        container.setConcurrency(annotation.concurrency());
        container.setBeanName(annotation.consumerName());
        return container;
    }

    private <T> JsonDeserializer<T> customizedJsonDeserializer(Class<T> eventClass) {
        ObjectMapper objectMapper = new GenericObjectMapper();
        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        var jsonDeserializer = new JsonDeserializer<>(eventClass, false);
        jsonDeserializer.addTrustedPackages("*");
        return jsonDeserializer;
    }

    private Map<String, Object> consumerProperties(Class<?> eventClass, GenericKafkaListener annotation, KafkaPropConfig kafkaPropConfig) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropConfig.getBootstrapAddress());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, annotation.consumerGroupId());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, customizedJsonDeserializer(eventClass));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, String.valueOf(annotation.enableAutoCommit()));
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, annotation.maxExecutionTime());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, annotation.maxPoolRecords());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, annotation.offsetResetStrategy().toLowerCase());
        props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, annotation.retryBackoffMs());
        props.put(ConsumerConfig.RECONNECT_BACKOFF_MS_CONFIG, annotation.reconnectBackoffMs());
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, annotation.consumerGroupId());
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, annotation.fetchMaxWaitMs());
        return props;
    }

    private <T> void registerContainer(ConcurrentMessageListenerContainer<String, T> container) {
        String containerName = container.getBeanName();
        var singletonObject = beanFactory.applyBeanPostProcessorsAfterInitialization(container, containerName);
        beanFactory.registerSingleton(containerName, singletonObject);
    }

    private <T> void addListener(
            ConcurrentMessageListenerContainer<String, T> container,
            Class<?> beanClass,
            Class<T> eventClass,
            Method method) {
        Object bean = beanFactory.getBean(beanClass);
        var messageListener = kafkaMessageListenerFactory.getMessageListener(bean, method, eventClass);
        container.setupMessageListener(messageListener);
    }
}
