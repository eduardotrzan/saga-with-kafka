package com.order.payment.paying.domain.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = { "com.order.payment.paying.domainentity"})
@EnableJpaRepositories("com.order.payment.paying.domain.repo")
public class PayingDomainConfig {

}
