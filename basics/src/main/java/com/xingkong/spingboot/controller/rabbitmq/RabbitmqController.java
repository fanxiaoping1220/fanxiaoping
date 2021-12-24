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

    @GetMapping(value = "/fanoutTest")
    public void fanoutTest(){
        rabbitTemplate.convertAndSend("sms","发送sms短信通知!");
        rabbitTemplate.convertAndSend("user","通知user!");
        rabbitTemplate.convertAndSend("email","发送email通知!");
    }

}