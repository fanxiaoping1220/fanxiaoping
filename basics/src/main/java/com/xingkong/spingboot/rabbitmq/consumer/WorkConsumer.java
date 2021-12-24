package com.xingkong.spingboot.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * * @className: WorkOneConsumer
 * * @description:轮询模式
 * * @author: fan xiaoping
 * * @date: 2021/12/23 0023 15:29
 **/
@Component
public class WorkConsumer {

    @RabbitListener(queues = "work")
    @RabbitHandler
    public void workOne(String data){
        System.out.println("workOne:开始消费消息..............");
        System.out.println(data);
    }

    @RabbitListener(queues = "work")
    @RabbitHandler
    public void workTwo(String data){
        System.out.println("workTwo:开始消费消息..............");
        System.out.println(data);
    }
}
