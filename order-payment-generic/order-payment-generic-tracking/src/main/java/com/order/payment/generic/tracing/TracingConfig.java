package com.order.payment.generic.tracing;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.sleuth.autoconfig.TraceAutoConfiguration;
import org.springframework.cloud.sleuth.log.SleuthLogAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({ SleuthLogAutoConfiguration.class, TraceAutoConfiguration.class})
public class TracingConfig {

}
