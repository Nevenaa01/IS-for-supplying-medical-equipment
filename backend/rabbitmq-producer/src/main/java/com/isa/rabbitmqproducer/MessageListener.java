package com.isa.rabbitmqproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
//    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);
    @RabbitListener(queues = MQConfig.QUEUE_RECEIVING)
    public void listener(CustomMessage message){
        //System.out.println(message);
        System.out.println(message.getMessage());
        //log.info(message.getMessage());
    }
}
