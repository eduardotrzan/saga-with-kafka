package com.order.payment.generic.kafka;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Type;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@RequiredArgsConstructor
public class DelegatingRecordMessageConverter extends MessagingMessageConverter {

    @Override
    public Message<?> toMessage(
            ConsumerRecord<?, ?> record,
            Acknowledgment acknowledgment,
            Consumer<?, ?> consumer,
            Type type) {
        Message<?> message = super.toMessage(record, acknowledgment, consumer, type);
        return MessageBuilder.createMessage(message.getPayload(), message.getHeaders());
    }
}
