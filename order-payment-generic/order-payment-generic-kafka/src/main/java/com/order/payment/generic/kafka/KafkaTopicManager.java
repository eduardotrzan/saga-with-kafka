package com.order.payment.generic.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.annotation.GenericKafkaEvent;

@Slf4j
@RequiredArgsConstructor
@Component
class KafkaTopicManager {

    private final AdminClient adminClient;

    void configure(Class<?> clazz) {
        try {
            GenericKafkaEvent KafkaEvent = clazz.getAnnotation(GenericKafkaEvent.class);
            log.debug("Found class={} with topic={}", clazz.getSimpleName(), KafkaEvent.topic());
            addTopicInKafka(adminClient, KafkaEvent);
        } catch (Exception e) {
            log.error("Exception happened for clazz={}.", clazz, e);
        }
    }

    private void addTopicInKafka(AdminClient adminClient, GenericKafkaEvent KafkaEvent) {
        try {
            final NewTopic newTopic = new NewTopic(KafkaEvent.topic(),
                                                   KafkaEvent.numPartitions(),
                                                   KafkaEvent.replicationFactor());
            // Create topic, which is async call.
            final CreateTopicsResult createTopicsResult = adminClient.createTopics(Collections.singleton(newTopic));

            // Since the call is Async, Lets wait for it to complete.
            createTopicsResult.values().get(newTopic.name()).get();

            log.info("Created new topic with name={}, numPartitions={} and replicationFactor={}.",
                     newTopic.name(),
                     newTopic.numPartitions(),
                     newTopic.replicationFactor());
        } catch (Exception e) {
            log.error("Topic={} already exists.", KafkaEvent.topic());
        }
    }
}
