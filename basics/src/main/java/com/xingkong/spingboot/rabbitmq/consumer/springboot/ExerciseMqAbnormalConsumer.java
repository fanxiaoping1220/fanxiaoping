package com.xingkong.spingboot.rabbitmq.consumer.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * * @className: ExerciseMqAbnormalConsumer
 * * @description: 处理消息消费异常
 * * @author: fan xiaoping
 * * @date: 2021/12/28 0028 12:19
 **/
@Slf4j
@Component
public class ExerciseMqAbnormalConsumer {

    @RabbitListener(queues = "dead.queue")
    @RabbitHandler
    public void abnormalConsumer(String data){
        //制造异常重试3次进入死信队列
        System.out.println(1/0);
        System.out.println(data);
    }


    @RabbitListener(queues = "handle.queue")
    @RabbitHandler
    public void deadTestReceiver(String data){
        log.info("将重试3次异常的消息存入数据库!");
        log.info("消息将放入死信队列:"+data);
    }
}
