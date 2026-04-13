package com.xingkong.spingboot.rabbitmq.producer.springboot;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * * @className: ExerciseFanoutProducer
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2021/12/24 0024 16:04
 **/
@Configuration
public class ExerciseFanoutProducer {

    @Bean
    public Queue smsQueue(){
        return new Queue("sms");
    }

    @Bean
    public Queue userQueue(){
        return new Queue("user");
    }

    @Bean
    public Queue emailQueue(){
        return new Queue("email");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    public Binding bindingSms(){
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingUser(){
        return BindingBuilder.bind(userQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingEmail(){
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }
}
