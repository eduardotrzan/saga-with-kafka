package com.order.payment.generic.kafka.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericKafkaListener {

    String topic();

    String consumerGroupId();

    String consumerName();

    int concurrency() default 1;

    int maxExecutionTime() default 30000;

    int maxPoolRecords() default 500;

    boolean enableAutoCommit() default false;

    String offsetResetStrategy() default "latest";

    int retryBackoffMs() default 1000;

    int reconnectBackoffMs() default 1000;

    int fetchMaxWaitMs() default 2000;

}
