package com.xingkong.spingboot.rabbitmq.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * * @className: ExerciseDirectProducer
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2021/12/24 0024 18:35
 **/
@Configuration
public class ExerciseDirectProducer {

    @Bean
    public Queue smsDirectQueue(){
        return new Queue("direct-sms");
    }

    @Bean
    public Queue emailDirectQueue(){
        return new Queue("direct-email");
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct-exchange");
    }

    @Bean
    public Binding smsBinding(){
        return BindingBuilder.bind(smsDirectQueue()).to(directExchange()).with("sms");
    }

    @Bean
    public Binding emailBinding(){
        return BindingBuilder.bind(emailDirectQueue()).to(directExchange()).with("email");
    }
}
