package com.xingkong.spingboot.rabbitmq.consumer.springboot;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * * @className: ExerciseTopicConsumer
 * * @description:采用注解模式
 * * @author: fan xiaoping
 * * @date: 2021/12/24 0024 19:56
 **/
@Component
public class ExerciseTopicConsumer {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.sms",durable = "true",autoDelete = "false"),
            exchange = @Exchange(value = "topic-exchange",type = ExchangeTypes.TOPIC),
            key = "#.sms.*"
    ))
    @RabbitHandler
    public void smsConsumer(String data){
        System.out.println("sms接收到消息:"+data);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "topic.email",durable = "true",autoDelete = "false"),
            exchange = @Exchange(value = "topic-exchange",type = ExchangeTypes.TOPIC),
            key = "*.email.#"
    ))
    @RabbitHandler
    public void emailConsumer(String data){
        System.out.println("email接收到消息:"+data);
    }
}
