package com.example.ISA2023.back.models;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    public static final String QUEUE = "messageQueue";
    public static final String QUEUE_FOR_MAP = "mapQueue";
    public static final String EXCHANGE = "messageExchange";
    public static final String MAP_EXCHANGE = "mapExchange";
    public static final String MESSAGE_ROUTING_KEY = "messageRoutingKey";
    public static final String MAP_ROUTING_KEY = "mapRoutingKey";
    public static final String QUEUE_SENDING = "messageQueueUser";
    public static final String EXCHANGE_SENDING = "messageExchangeUser";
    public static final String MESSAGE_ROUTING_KEY_SENDING = "messageRoutingKeyUser";
    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }
    @Bean
    public Queue queueSending() {
        return new Queue(QUEUE_SENDING);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public TopicExchange exchangeSending() {
        return new TopicExchange(EXCHANGE_SENDING);
    }

    /*@Bean
    public Binding binding(Queue queue, @Qualifier("exchange") TopicExchange topicExchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange())
                .with(MESSAGE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingSending(Queue queueSending, @Qualifier("exchangeSending") TopicExchange topicExchange){
        return BindingBuilder
                .bind(queueSending)
                .to(exchangeSending())
                .with(MESSAGE_ROUTING_KEY_SENDING);
    }*/

    @Bean
    public MessageConverter messageConverer() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setMessageConverter(messageConverer());
        return template;
    }


    @Bean public Queue queueMap()
    {
        return new Queue(QUEUE_FOR_MAP);
    }

    @Bean public TopicExchange exchangeMap()
    {
        return new TopicExchange(MAP_EXCHANGE);
    }

    @Bean
    public Binding bindingMap(Queue queue,  @Qualifier("exchange") TopicExchange exchange)
    {
        return BindingBuilder.bind(queue)
                .to(exchangeMap())
                .with(MAP_ROUTING_KEY);
    }

}
