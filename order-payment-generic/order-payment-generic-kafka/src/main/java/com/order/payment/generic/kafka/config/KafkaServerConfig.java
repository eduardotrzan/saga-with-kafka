package com.order.payment.generic.kafka.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class KafkaServerConfig {
}
