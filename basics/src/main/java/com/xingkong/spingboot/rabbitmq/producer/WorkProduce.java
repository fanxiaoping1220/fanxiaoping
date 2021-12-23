package com.xingkong.spingboot.rabbitmq.producer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * * @className: WorkProduce
 * * @description: work模式轮询消费
 * * @author: fan xiaoping
 * * @date: 2021/12/23 0023 15:22
 **/
@Component
public class WorkProduce {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Bean
    public Queue queue(){
        return new Queue("work");
    }

    public void sendMessage(){
        for (int i = 0; i < 20; i++) {
            String message = "发送消息message:"+i;
            rabbitTemplate.convertAndSend("work",message);
        }
    }
}
