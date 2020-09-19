package com.order.payment.ordering.service.event.incoming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.order.payment.generic.kafka.annotation.GenericKafkaListener;
import com.order.payment.ordering.domain.entity.Order;
import com.order.payment.ordering.domain.entity.enums.OrderStatus;
import com.order.payment.ordering.service.business.OrderService;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderPayedSuccessEventListener {

    private final OrderService orderService;

    @Transactional
    @GenericKafkaListener(topic = OrderPayedSuccessEvent.TOPIC,
                          consumerGroupId = OrderPayedSuccessEvent.CONSUMER_GROUP_ID,
                          consumerName = OrderPayedSuccessEvent.CONSUMER_NAME)
    public void handle(OrderPayedSuccessEvent event) {
        log.info("Processing event={}.", event);
        Order updateOrder = Order.builder()
                .uuid(event.getOrderUuid())
                .status(OrderStatus.PAID)
                .build();
        this.orderService.update(updateOrder);
        log.info("finished event");
    }

}
