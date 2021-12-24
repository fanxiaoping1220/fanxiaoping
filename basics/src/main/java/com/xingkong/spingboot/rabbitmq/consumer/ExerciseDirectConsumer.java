package com.xingkong.spingboot.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * * @className: ExerciseDirectConsumer
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2021/12/24 0024 18:41
 **/
@Component
public class ExerciseDirectConsumer {

    @RabbitListener(queues = "direct-sms")
    @RabbitHandler
    public void smsConsumer(String data){
        System.out.println("sms接收消息:"+data);
    }

    @RabbitListener(queues = "direct-email")
    @RabbitHandler
    public void emailConsumer(String data){
        System.out.println("email接收消息:"+data);
    }
}
