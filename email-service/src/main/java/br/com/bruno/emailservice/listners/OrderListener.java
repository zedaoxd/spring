package br.com.bruno.emailservice.listners;

import lombok.extern.slf4j.Slf4j;
import models.dtos.OrderCreatedMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.queue.order-created}"),
            exchange = @Exchange(value = "${spring.rabbitmq.exchange.order}", type = "${spring.rabbitmq.exchange.type}"),
            key = "${spring.rabbitmq.routing-key.order-created}"
    ))
    public void listener(final OrderCreatedMessage orderCreatedMessage) {
        log.info("Order created message received: {}", orderCreatedMessage);
    }
}
