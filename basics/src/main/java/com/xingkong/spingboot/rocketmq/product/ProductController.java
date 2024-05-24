package com.xingkong.spingboot.rocketmq.product;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * * @className: ProductController
 * * @description: 消息生产者
 * * @author: fan xiaoping
 * * @date: 2024/5/23 0023 16:03
 **/
@Slf4j
@RequestMapping(value = "/rocketmq")
@RestController
public class ProductController {

    @Autowired
    private RocketMQTemplate rocketmqTemplate;

    /**
     * 发送消息
     * @param message
     * @return
     */
    @PostMapping(value = "/send")
    public String sendMsg(@RequestParam(name = "message") String message){
        rocketmqTemplate.convertAndSend("test-topic",message);
        log.info("send msg:{}",message);
        return "message:"+message;
    }

    /**
     * 发送消息:多个订阅的消息者消费
     * @param message
     * @return
     */
    @PostMapping(value = "/dailySendMsg")
    public String dailySendMsg(@RequestParam(name = "message") String message) {
        rocketmqTemplate.convertAndSend("test-daily-topic",message);
        log.info("daily send msg:{}", message);
        return "message:"+message;
    }
}
