package com.order.payment.paying.server.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import com.order.payment.generic.kafka.config.KafkaPropConfig;
import com.order.payment.generic.security.SpringSecurityConfig;
import com.order.payment.generic.server.filter.config.ServerFilterConfig;
import com.order.payment.generic.tracing.TracingConfig;
import com.order.payment.paying.controller.config.PayingControllerConfig;

@Import({
                PayingControllerConfig.class,

                ServerFilterConfig.class,
                SpringSecurityConfig.class,
                TracingConfig.class,

        })
@EnableConfigurationProperties({
                                       PayingServerPropConfig.class,
                                       KafkaPropConfig.class
                               })
public class PayingServerConfig {

}
