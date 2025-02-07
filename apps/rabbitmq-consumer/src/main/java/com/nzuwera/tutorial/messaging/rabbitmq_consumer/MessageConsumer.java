package com.nzuwera.tutorial.messaging.rabbitmq_consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumer {

    @RabbitListener(queues = ConsumerRabbitMQConfig.CLASSIC_QUEUE)
    public void classicMessageReceiver(String message) {
        log.info("Received classic message: {}", message);
    }

    @RabbitListener(queues = ConsumerRabbitMQConfig.QUORUM_QUEUE)
    public void quorumMessageReceiver(String message) {
        log.info("Received quorum message: {}", message);
    }
}
