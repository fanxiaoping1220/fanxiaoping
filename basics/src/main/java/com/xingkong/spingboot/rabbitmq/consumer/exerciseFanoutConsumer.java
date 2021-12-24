package com.xingkong.spingboot.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * * @className: exerciseFanoutConsumer
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2021/12/24 0024 16:16
 **/
@Component
public class exerciseFanoutConsumer {

    @RabbitHandler
    @RabbitListener(queues = "sms")
    public void smsConsumer(String data){
        System.out.println(data);
    }

    @RabbitListener(queues = "user")
    @RabbitHandler
    public void userConsumer(String data){
        System.out.println(data);
    }

    @RabbitListener(queues = "email")
    @RabbitHandler
    public void emailConsumer(String data){
        System.out.println(data);
    }
}
