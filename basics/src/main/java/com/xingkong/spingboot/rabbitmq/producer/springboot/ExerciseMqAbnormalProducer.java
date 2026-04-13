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
 * * @className: ExerciseMqAbnormalProducer
 * * @description: 处理消息消费失败，消息通过死信队列，转移到另一个队列，进行数据的存储
 * 重发3次之后,通过死信队列转移到handleQueue，进行处理，最终将失败的保存到数据库，进行一个数据保存
 * {@link com.xingkong.spingboot.rabbitmq.consumer.springboot.ExerciseMqAbnormalConsumer}
 * {@link com.xingkong.spingboot.controller.rabbitmq.RabbitmqController.HandleDeadTest}
 *  控制重发次数+死信队列+二次处理存入数据库
 * * @author: fan xiaoping
 * * @date: 2021/12/28 0028 11:54
 **/
@Configuration
public class ExerciseMqAbnormalProducer {

    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue deadQueue(){
        Map<String,Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange","handle.exchange");
        args.put("x-dead-letter-routing-key","dead");
        return new Queue("dead.queue",true,false,false,args);
    }

    /**
     * 死信队列交换机
     * @return
     */
    @Bean
    public DirectExchange directDeadExchange(){
        return new DirectExchange("direct.dead.exchange");
    }

    @Bean
    public Binding deadBinding(){
        return BindingBuilder.bind(deadQueue()).to(directDeadExchange()).with("abnormal");
    }

    /**
     * 处理异常的队列
     * 重发3次之后的消息
     * @return
     */
    @Bean
    public Queue handleQueue(){
        return new Queue("handle.queue");
    }

    /**
     * 处理异常的交换机
     * @return
     */
    @Bean
    public DirectExchange handleExchange(){
        return new DirectExchange("handle.exchange");
    }

    @Bean
    public Binding handleBinding(){
        return BindingBuilder.bind(handleQueue()).to(handleExchange()).with("dead");
    }
}
