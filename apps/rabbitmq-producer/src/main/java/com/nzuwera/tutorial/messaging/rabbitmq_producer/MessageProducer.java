package com.nzuwera.tutorial.messaging.rabbitmq_producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class MessageProducer {

    @Value("${spring.profiles.active}")
    private String applicationProfile;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void produceClassicMessages() {
        String message = "Hello! Current time is %s in classic queues".formatted(LocalDateTime.now());
        rabbitTemplate.convertAndSend(ProducerRabbitMQConfig.FANOUT_CLASSIC_EXCHANGE, "", message);
        logMessage(applicationProfile, message);
    }


    @Scheduled(fixedRate = 5000)
    public void produceQuorumMessages() {
        String message = "Hello! Current time is %s in quorum queues".formatted(LocalDateTime.now());
        rabbitTemplate.convertAndSend(ProducerRabbitMQConfig.FANOUT_EXCHANGE, "", message);
        logMessage(applicationProfile, message);
    }


    @Scheduled(fixedRate = 5000)
    public void produceFixedQuorumMessages() {
        String message = "Hello! Current time is %s in quorum queues".formatted(LocalDateTime.now());
        rabbitTemplate.convertAndSend(ProducerRabbitMQConfig.FANOUT_FIXED_EXCHANGE, "", message);
        logMessage(applicationProfile, message);
    }

    private void logMessage(String profile, String message) {
        log.info("{} - Sent message: {}", profile, message);
    }
}