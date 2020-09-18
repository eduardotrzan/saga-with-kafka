package com.order.payment.generic.kafka;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import com.order.payment.generic.kafka.annotation.GenericKafkaEvent;

@RequiredArgsConstructor
@Component
public class KafkaTemplateManager {

    private final ConfigurableListableBeanFactory beanFactory;
    private final GenericWebApplicationContext context;

    <T> void configure(Class<T> clazz) {
        GenericKafkaEvent annotation = clazz.getAnnotation(GenericKafkaEvent.class);
        registerTemplate(annotation);
    }

    private void registerTemplate(GenericKafkaEvent annotation) {
        String beanName = annotation.templateName();
        context.registerBean(beanName,
                             KafkaTemplate.class,
                             () -> createKafkaTemplate(annotation),
                             bd -> bd.setAutowireCandidate(true));
    }

    private <T> KafkaTemplate<String, T> createKafkaTemplate(GenericKafkaEvent annotation) {
        var producer = beanFactory.getBean(annotation.producerName(), DefaultKafkaProducerFactory.class);
        return new KafkaTemplate<String, T>(producer);
    }

    public <T> KafkaTemplate<String, T> getKafkaTemplate(Class<T> clazz) {
        GenericKafkaEvent annotation = clazz.getAnnotation(GenericKafkaEvent.class);
        var template = (KafkaTemplate<String, T>) beanFactory.getBean(annotation.templateName());
        template.setApplicationContext(this.context);
        return template;
    }
}
