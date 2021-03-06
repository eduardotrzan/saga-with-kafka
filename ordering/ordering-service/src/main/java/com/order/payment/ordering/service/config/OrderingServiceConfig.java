package com.order.payment.ordering.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.order.payment.generic.kafka.config.KafkaServerConfig;
import com.order.payment.ordering.domain.config.OrderingDomainConfig;

@Configuration
@ComponentScan(basePackages = "com.order.payment.ordering.service")
@Import({ OrderingDomainConfig.class, KafkaServerConfig.class })
public class OrderingServiceConfig {

}
