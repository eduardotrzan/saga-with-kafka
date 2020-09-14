package com.order.payment.generic.kafka.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
public @interface GenericKafkaEvent {

    String topic();

    int numPartitions() default 1;

    short replicationFactor() default 1;

    String producerName() default "";

    String templateName() default "";

    int retryBackoffMs() default 1000;

    int reconnectBackoffMs() default 1000;

    int lingerMs() default 1;

    String ackMode() default "all";

    boolean enableIdempotence() default true;
}
