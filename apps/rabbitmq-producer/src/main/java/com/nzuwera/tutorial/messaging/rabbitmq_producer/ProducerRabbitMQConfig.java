package com.nzuwera.tutorial.messaging.rabbitmq_producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProducerRabbitMQConfig {
    public static final String CLASSIC_QUEUE = "time-queue";
    public static final String FANOUT_EXCHANGE = "fanout-exchange";
    public static final String FANOUT_CLASSIC_EXCHANGE = "fanout-classic-exchange";
    public static final String FANOUT_FIXED_EXCHANGE = "fanout-fixed-exchange";
    public static final String QUORUM_QUEUE = "time-quorum-queue";
    public static final String FIXED_QUORUM_QUEUE = "fixed-quorum-queue";
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    @Bean
    public FanoutExchange fanoutClassicExchange() {
        return new FanoutExchange(FANOUT_CLASSIC_EXCHANGE);
    }
    @Bean
    public FanoutExchange fanoutFixedExchange() {
        return new FanoutExchange(FANOUT_FIXED_EXCHANGE);
    }

    @Bean
    public Queue classicQueue() {
        return new Queue(CLASSIC_QUEUE, true,false,false);
    }

    @Bean
    public Binding classicBinding() {
        return BindingBuilder.bind(classicQueue()).to(fanoutClassicExchange());
    }

    @Bean
    public Binding quorumBinding() {
        return BindingBuilder.bind(quorumQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fixedQuorumBinding() {
        return BindingBuilder.bind(fixedQuorumQueue()).to(fanoutFixedExchange());
    }

    @Bean
    public Queue fixedQuorumQueue() {
        return new Queue(FIXED_QUORUM_QUEUE);
    }

    @Bean
    public Queue quorumQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-type", "quorum");  // Set queue type to quorum
        return new Queue(QUORUM_QUEUE, true, false, false, args);
    }
    /**
    // In case you need to process json messages
    // Enable the below configuration


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
    */
}
