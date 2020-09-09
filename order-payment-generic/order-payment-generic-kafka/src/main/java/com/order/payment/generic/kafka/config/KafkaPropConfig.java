package com.order.payment.generic.kafka.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = {"bootstrapAddress", "clientId"})
@ConfigurationProperties(prefix = "kafka")
public class KafkaPropConfig {

    private String bootstrapAddress;

    private String clientId;

}
