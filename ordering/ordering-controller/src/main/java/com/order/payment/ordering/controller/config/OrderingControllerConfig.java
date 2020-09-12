package com.order.payment.ordering.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.order.payment.ordering.service.config.OrderingServiceConfig;

@Configuration
@ComponentScan(basePackages = "com.order.payment.ordering.controller")
@Import({ OrderingServiceConfig.class })
public class OrderingControllerConfig {

}
