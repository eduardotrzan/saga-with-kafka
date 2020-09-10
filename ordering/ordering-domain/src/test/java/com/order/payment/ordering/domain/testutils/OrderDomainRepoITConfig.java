package com.order.payment.ordering.domain.testutils;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = { "com.order.payment.ordering.domain.repo" })
@EntityScan(basePackages = { "com.order.payment.ordering.domain.entity" })
@EnableTransactionManagement
@EnableConfigurationProperties
@ComponentScan(basePackageClasses = OrderDatabasePrefillUtils.class)
class OrderDomainRepoITConfig {

}
