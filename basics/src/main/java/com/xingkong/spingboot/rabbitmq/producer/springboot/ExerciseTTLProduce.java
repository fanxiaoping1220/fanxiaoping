package com.xingkong.spingboot.rabbitmq.producer.springboot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * * @className: ExerciseTTLProduce
 * * @description: 设置过期时间  参数：x-message-ttl 单位毫秒
 * * @author: fan xiaoping
 * * @date: 2021/12/27 0027 12:01
 **/
@Configuration
public class ExerciseTTLProduce {

    @Bean
    public Queue ttlQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",5000);
        return new Queue("ttl-queue",true,false,false,args);
    }

    @Bean
    public DirectExchange ttlExchange(){
        return new DirectExchange("ttl-exchange");
    }

    @Bean
    public Binding ttlBinding(){
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with("ttl");
    }

    @Bean
    public Queue ttlMessageQueue(){
        return new Queue("ttl-message-queue");
    }

    @Bean
    public Binding ttlMessageBinding(){
        return BindingBuilder.bind(ttlMessageQueue()).to(ttlExchange()).with("ttl.message");
    }
}
