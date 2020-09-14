package com.order.payment.generic.server.filter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.order.payment.generic.server.filter.RequestSessionIdFilter;

@Configuration
public class ServerFilterConfig {

    @Bean
    public RequestSessionIdFilter requestSessionIdFilter() {
        return new RequestSessionIdFilter();
    }
}
