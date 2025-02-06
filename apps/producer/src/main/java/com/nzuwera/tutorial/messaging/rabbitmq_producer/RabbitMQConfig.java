package com.nzuwera.tutorial.messaging.rabbitmq_producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    public static final String CLASSIC_QUEUE = "time-queue";
    public static final String CLASSIC_EXCHANGE = "classic_exchange";
    public static final String CLASSIC_ROUTING_KEY = "classic";
    public static final String QUORUM_QUEUE = "time-quorum-queue";
    public static final String QUORUM_EXCHANGE = "quorum_exchange";
    public static final String QUORUM_ROUTING_KEY = "quorum";

    @Bean
    public Queue classicQueue() {
        return new Queue(CLASSIC_QUEUE, false);
    }

    @Bean
    public Binding classicBinding() {
        return BindingBuilder.bind(classicQueue()).to(new TopicExchange(CLASSIC_EXCHANGE)).with(CLASSIC_ROUTING_KEY);
    }

    @Bean
    public Binding quorumBinding() {
        return BindingBuilder.bind(quorumQueue()).to(new TopicExchange(QUORUM_EXCHANGE)).with(QUORUM_ROUTING_KEY);
    }

    @Bean
    public Queue quorumQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "quorum");  // Set queue type to quorum
        return new Queue(QUORUM_QUEUE, true, false, false, args);
    }
}
