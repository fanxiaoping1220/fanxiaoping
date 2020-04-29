package com.xingkong.spingboot.controller.rabbitmq;

import com.xingkong.spingboot.rabbitmq.producer.FanoutProduce;
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

    @GetMapping(value = "/exchange/fanout")
    public void exchangeTypeFanout() {
        fanoutProduce.fanoutSend("发送消息:fanout类型。123456");
    }

}