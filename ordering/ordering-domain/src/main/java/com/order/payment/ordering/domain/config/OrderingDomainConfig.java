package com.order.payment.ordering.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = { "com.order.payment.ordering.domain.entity"})
@EnableJpaRepositories("com.order.payment.ordering.domain.repo")
public class OrderingDomainConfig {

}
