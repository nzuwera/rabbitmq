package com.nzuwera.tutorial.messaging.rabbitmq_consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConsumer {

    @Value("${spring.profiles.active}")
    private String applicationProfile;

    @RabbitListener(queues = ConsumerRabbitMQConfig.CLASSIC_QUEUE)
    public void classicMessageReceiver(String message) {
        log.info("Received {} classic message: {}",applicationProfile, message);
    }

    @RabbitListener(queues = ConsumerRabbitMQConfig.QUORUM_QUEUE)
    public void quorumMessageReceiver(String message) {
        log.info("Received {} quorum message: {}",applicationProfile, message);
    }
}
