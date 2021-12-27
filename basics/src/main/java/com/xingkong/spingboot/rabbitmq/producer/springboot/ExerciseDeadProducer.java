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
 * * @className: ExerciseDeadProducer
 * * @description: 死信队列
 * * @author: fan xiaoping
 * * @date: 2021/12/27 0027 14:39
 **/
@Configuration
public class ExerciseDeadProducer {

    @Bean
    public Queue ttlExpirationQueue(){
        Map<String,Object> args = new HashMap<>();
        //设置过期时间
        args.put("x-message-ttl",5000);
        //设置死信交换机
        args.put("x-dead-letter-exchange","dead-exchange");
        //设置死信routingKey
        args.put("x-dead-letter-routing-key","dead");
        return new Queue("ttl-expiration-queue",true,false,false,args);
    }

    @Bean
    public DirectExchange ttlExpirationExchange(){
        return new DirectExchange("ttl-expiration-exchange");
    }

    @Bean
    public Binding ttlExpirationBinding(){
        return BindingBuilder.bind(ttlExpirationQueue()).to(ttlExpirationExchange()).with("ttl");
    }

    @Bean
    public Queue deadQueue(){
        return new Queue("dead-queue");
    }

    @Bean
    public DirectExchange deadExchange(){
        return new DirectExchange("dead-exchange");
    }

    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("dead");
    }
}
