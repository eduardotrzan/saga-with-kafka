package com.order.payment.paying.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.order.payment.paying.service.config.PayingServiceConfig;

@Configuration
@ComponentScan(basePackages = "com.order.payment.paying.controller")
@Import({ PayingServiceConfig.class })
public class PayingControllerConfig {

}
