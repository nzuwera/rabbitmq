package com.nzuwera.tutorial.messaging.rabbitmq_consumer;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerRabbitMQConfig {
    public static final String CLASSIC_QUEUE = "time-queue";
    public static final String QUORUM_QUEUE = "time-quorum-queue";

}
