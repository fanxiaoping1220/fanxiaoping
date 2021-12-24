package com.xingkong.spingboot.controller.rabbitmq;

import com.xingkong.spingboot.rabbitmq.producer.FanoutProduce;
import com.xingkong.spingboot.rabbitmq.producer.WorkProduce;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: RabbitmqController
 * @description:
 * @author: 范小平
 * @date: 2019-10-09 18:14
 * @version: 1.0.0
 */
@RestController
@RequestMapping(value = "/rabbitmq")
public class RabbitmqController {

    @Autowired
    private FanoutProduce fanoutProduce;

    @Autowired
    private WorkProduce workProduce;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping(value = "/exchange/fanout")
    public void exchangeTypeFanout() {
        fanoutProduce.fanoutSend("发送消息:fanout类型。123456");
    }

    @GetMapping(value = "/workTest")
    public void workTest(){
        workProduce.sendMessage();
    }

    /**
     * 发送一个消息通知sms,user,email同时接收到消息
     */
    @GetMapping(value = "/fanoutTest")
    public void fanoutTest(){
        rabbitTemplate.convertAndSend("fanout-exchange","","发送通知!");
    }

    /**
     * 分别给sms,email发送消息
     */
    @GetMapping(value = "/directTest")
    public void directTest(){
        rabbitTemplate.convertAndSend("direct-exchange","sms","发送消息给sms业务!");
        rabbitTemplate.convertAndSend("direct-exchange","email","发送消息给email业务!");
    }

    @GetMapping(value = "/topicTest")
    public void topicTest(){
        //#.sms.*
        //*.email.#
        String routingKey = "sms.email";
        rabbitTemplate.convertAndSend("topic-exchange",routingKey,"发送通知!");
    }

}