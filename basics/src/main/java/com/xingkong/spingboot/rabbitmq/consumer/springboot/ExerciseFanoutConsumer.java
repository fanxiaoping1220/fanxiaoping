package com.xingkong.spingboot.rabbitmq.consumer.springboot;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * * @className: exerciseFanoutConsumer
 * * @description:sms,user,email接收消息
 * * @author: fan xiaoping
 * * @date: 2021/12/24 0024 16:16
 **/
@Component
public class ExerciseFanoutConsumer {

    @RabbitHandler
    @RabbitListener(queues = "sms")
    public void smsConsumer(String data){
        System.out.println(data+"sms接收到通知");
    }

    @RabbitListener(queues = "user")
    @RabbitHandler
    public void userConsumer(String data){
        System.out.println(data+"user接收通知");
    }

    @RabbitListener(queues = "email")
    @RabbitHandler
    public void emailConsumer(String data){
        System.out.println(data+"email接收通知");
    }
}
