package com.order.payment.generic.kafka;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageListenerFactory {

    MessageListener<String, Object> getMessageListener(Object bean, Method method, Class<?> eventClass) {
        return new MessageListener<>() {

            @Override
            public void onMessage(ConsumerRecord<String, Object> consumerRecord) {
                Message<?> eventMessage = extractEventMessage(consumerRecord);
                try {
                    method.invoke(bean, eventMessage.getPayload());
                } catch (Exception e) {
                    log.error("Event listener failed with exception", e);
                }
            }

            private Message<?> extractEventMessage(ConsumerRecord<String, Object> consumerRecord) {
                var recordConverter = new DelegatingRecordMessageConverter();
                return recordConverter.toMessage(consumerRecord, null, null, eventClass);
            }
        };
    }
}