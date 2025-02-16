package com.nzuwera.tutorial.messaging.rabbitmq_consumer;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerRabbitMQConfig {
    public static final String CLASSIC_QUEUE = "time-queue";
    public static final String QUORUM_QUEUE = "time-quorum-queue";
    public static final String QUORUM_FIXED_QUEUE = "fixed-quorum-queue";

}
