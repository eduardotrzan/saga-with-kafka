package com.order.payment.ordering.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.order.payment.generic.security.SpringSecurityConfig;
import com.order.payment.generic.tracing.TracingConfig;
import com.order.payment.ordering.controller.config.OrderControllerConfig;
import com.order.payment.ordering.server.config.OrderServerConfig;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({
                                       OrderServerConfig.class
                               })
@Import({
                OrderControllerConfig.class,

                SpringSecurityConfig.class,
                TracingConfig.class,

        })
@SpringBootApplication
public class OrderApplication implements CommandLineRunner {

    private final OrderServerConfig config;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @PostConstruct
    private void started() {
        TimeZone.setDefault(TimeZone.getTimeZone(this.config.getTimeZone()));
    }

    @Override
    public void run(String... strings) {
        log.info("Running system with config={}.", this.config);
    }
}
