package com.order.payment.ordering.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.order.payment.ordering.server.config.OrderServerConfig;
import com.order.payment.ordering.server.config.OrderServerPropConfig;

@Slf4j
@RequiredArgsConstructor
@Import({ OrderServerConfig.class })
@SpringBootApplication
public class OrderApplication implements CommandLineRunner {

    private final OrderServerPropConfig config;

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
