package com.order.payment.paying.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.order.payment.paying.domain.config.PayingDomainConfig;

@Configuration
@ComponentScan(basePackages = "com.order.payment.paying.service")
@Import({ PayingDomainConfig.class })
public class PayingServiceConfig {

}
