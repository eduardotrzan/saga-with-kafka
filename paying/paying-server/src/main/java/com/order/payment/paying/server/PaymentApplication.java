package com.order.payment.paying.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.order.payment.generic.kafka.config.KafkaPropConfig;
import com.order.payment.generic.security.SpringSecurityConfig;
import com.order.payment.generic.tracing.TracingConfig;
import com.order.payment.paying.controller.config.PayingControllerConfig;
import com.order.payment.paying.server.config.PayingServerConfig;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties({
                                       PayingServerConfig.class,
                                       KafkaPropConfig.class
                               })
@Import({
                PayingControllerConfig.class,

                SpringSecurityConfig.class,
                TracingConfig.class,

        })
@SpringBootApplication
public class PaymentApplication implements CommandLineRunner {

    private final PayingServerConfig config;

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
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
