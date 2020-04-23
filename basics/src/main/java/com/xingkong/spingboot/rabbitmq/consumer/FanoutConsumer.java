package com.xingkong.spingboot.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @className: FanoutConsumer
 * @description: fanout广播模式消费 不知道队里名称
 * @author: 范小平
 * @date: 2019-10-09 18:09
 * @version: 1.0.0
 */
@Component
public class FanoutConsumer {

    /**
     * 消费消息不指定队里名称
     * @param message
     */
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    @RabbitHandler
    public void fanoutConsumer(String message){
        System.out.println(message);
    }
}