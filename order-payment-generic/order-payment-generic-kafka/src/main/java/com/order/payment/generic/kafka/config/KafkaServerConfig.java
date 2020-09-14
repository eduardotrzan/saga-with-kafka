package com.order.payment.generic.kafka.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.order.payment.generic.kafka")
@EnableAutoConfiguration(exclude = KafkaAutoConfiguration.class)
@EnableConfigurationProperties({
                                       KafkaPropConfig.class
                               })
public class KafkaServerConfig {

}
