package com.order.payment.generic.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.order.payment.generic.kafka.config.KafkaPropConfig;

@Component
public class KafkaAdminClientConfig {

    @Bean
    public AdminClient adminClient(KafkaPropConfig kafkaPropConfig) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropConfig.getServerHost());
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, kafkaPropConfig.getClientId());
        return org.apache.kafka.clients.admin.KafkaAdminClient.create(configs);
    }
}
