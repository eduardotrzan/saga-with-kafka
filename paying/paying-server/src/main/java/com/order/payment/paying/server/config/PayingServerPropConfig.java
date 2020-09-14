package com.order.payment.paying.server.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@ConfigurationProperties(prefix = "config")
public class PayingServerPropConfig {

    @ToString.Include
    private String serviceName;

    @ToString.Include
    private String timeZone;

}
