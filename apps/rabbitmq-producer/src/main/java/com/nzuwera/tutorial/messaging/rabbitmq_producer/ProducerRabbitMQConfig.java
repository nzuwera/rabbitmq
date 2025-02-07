package com.nzuwera.tutorial.messaging.rabbitmq_producer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerRabbitMQConfig {
    public static final String CLASSIC_QUEUE = "time-queue";
    public static final String FANOUT_EXCHANGE = "fanout-exchange";
    public static final String QUORUM_QUEUE = "time-quorum-queue";
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean
    public Queue classicQueue() {
        return new Queue(CLASSIC_QUEUE, false);
    }

    @Bean
    public Binding classicBinding() {
        return BindingBuilder.bind(classicQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding quorumBinding() {
        return BindingBuilder.bind(quorumQueue()).to(fanoutExchange());
    }

    @Bean
    public Queue quorumQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "quorum");  // Set queue type to quorum
        return new Queue(QUORUM_QUEUE, true, false, false, args);
    }
/*
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }*/
}
