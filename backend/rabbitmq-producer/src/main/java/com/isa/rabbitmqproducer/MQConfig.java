package com.isa.rabbitmqproducer;

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
    public static final String EXCHANGE = "messageExchange";
    public static final String MESSAGE_ROUTING_KEY = "messageRoutingKey";

    public static final String QUEUE_RECEIVING = "messageQueueUser";
    public static final String EXCHANGE_RECEIVING = "messageExchangeUser";
    public static final String MESSAGE_ROUTING_KEY_RECEIVING = "messageRoutingKeyUser";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }
    @Bean
    public Queue queueReceiving() {
        return new Queue(QUEUE_RECEIVING);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }
    @Bean
    public TopicExchange exchangeReceiving() {
        return new TopicExchange(EXCHANGE_RECEIVING);
    }

    /*@Bean
    public Binding binding(Queue queue, @Qualifier("exchange") TopicExchange topicExchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange())
                .with(MESSAGE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingReceiving(Queue queueReceiving, @Qualifier("exchangeReceiving") TopicExchange topicExchange){
        return BindingBuilder
                .bind(queueReceiving)
                .to(exchangeReceiving())
                .with(MESSAGE_ROUTING_KEY_RECEIVING);
    }*/

    @Bean
    public MessageConverter messageConverer() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);

        template.setMessageConverter(messageConverer());
        return template;
    }
}
