package com.order.payment.paying.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.order.payment.paying.server.config.PayingServerConfig;
import com.order.payment.paying.server.config.PayingServerPropConfig;

@Slf4j
@RequiredArgsConstructor
@Import({ PayingServerConfig.class })
@SpringBootApplication
public class PaymentApplication implements CommandLineRunner {

    private final PayingServerPropConfig config;

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
