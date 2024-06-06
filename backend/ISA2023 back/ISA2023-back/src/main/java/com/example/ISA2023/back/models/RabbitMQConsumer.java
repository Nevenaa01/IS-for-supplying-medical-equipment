package com.example.ISA2023.back.models;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "mapQueue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }


}