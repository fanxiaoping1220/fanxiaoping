package com.xingkong.spingboot.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @className: FanoutProduce
 * @description: fanout
 * @author: 范小平
 * @date: 2019-10-09 17:18
 * @version: 1.0.0
 */
@Component
public class FanoutProduce {

    private static final String EXCHANGE_FANOUT = "exchange.fanout";

    @Value(value = "${rabbitmq.queue.name}")
    private String name;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue fanoutQueue(){
        return new Queue(name);
    }

    /**
     * 创建type = fanout 的交换机
     * @return
     */
    @Bean
    public FanoutExchange exchange(){
        return new FanoutExchange(EXCHANGE_FANOUT);
    }

    /**
     * 将队里绑定交换机
     * @return
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(fanoutQueue()).to(exchange());
    }

    /**
     * 发送消息
     * ExchangeType = fanout
     * @param message
     */
    public void fanoutSend(String message){
        rabbitTemplate.convertAndSend(EXCHANGE_FANOUT,"",message);
    }

}