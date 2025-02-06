package com.nzuwera.tutorial.messaging.rabbitmq_producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class QuorumMessageSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public QuorumMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        String message = "Hello! Current time is %s in quorum queues".formatted(LocalDateTime.now());
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUORUM_EXCHANGE, RabbitMQConfig.QUORUM_ROUTING_KEY, message);
        System.out.println("Sent message: " + message);
    }
}