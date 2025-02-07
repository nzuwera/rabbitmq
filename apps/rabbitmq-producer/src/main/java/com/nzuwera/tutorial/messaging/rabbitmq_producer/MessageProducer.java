package com.nzuwera.tutorial.messaging.rabbitmq_producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void produceClassicMessages() {
        String message = "Hello! Current time is %s in classic queues".formatted(LocalDateTime.now());
        rabbitTemplate.convertAndSend(ProducerRabbitMQConfig.FANOUT_EXCHANGE, "", message);
        System.out.println("Sent message: " + message);
    }


    @Scheduled(fixedRate = 5000)
    public void produceQuorumMessages() {
        String message = "Hello! Current time is %s in quorum queues".formatted(LocalDateTime.now());
        rabbitTemplate.convertAndSend(ProducerRabbitMQConfig.FANOUT_EXCHANGE, "", message);
        System.out.println("Sent message: " + message);
    }
}