package com.order.payment.generic.kafka.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.order.payment.generic.kafka")
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
public class KafkaServerConfig {

}
