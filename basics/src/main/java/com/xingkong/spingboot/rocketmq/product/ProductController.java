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

    /**
     * 发送同步消息
     * @param message
     * @return
     */
    @PostMapping(value = "/syncSendMsg")
    public String syncSendMsg(@RequestParam(name = "message") String message){
        rocketmqTemplate.syncSend("sync-message",message);
        log.info("sync message:{}",message);
        return message;
    }

    /**
     * 异步发送消息
     * @param message
     * @return
     */
    @PostMapping(value = "/asyncSendMsg")
    public String asyncSendMsg(@RequestParam(name = "message") String message){
        rocketmqTemplate.asyncSend("async-message",message,null);
        log.info("async message:{}",message);
        return message;
    }

    /**
     * 发送单向消息
     * @param message
     * @return
     */
    @PostMapping(value = "/oneWaySendMsg")
    public String oneWaySendMsg(@RequestParam(name = "message") String message){
        rocketmqTemplate.sendOneWay("one-way-message",message);
        log.info("one way message:{}",message);
        return message;
    }

    /**
     * 顺序消息: 分区有序消息
     * 通过hashKey(选择key)转换为hashcode来选择存放的queue
     * MessageQueueSelector messageQueueSelector = new SelectMessageQueueByHash()
     * @param message
     * @return
     */
    @PostMapping(value = "/partitionSortSendMsg")
    public String partitionSortSendMsg(@RequestParam(name = "message") String message){
        rocketmqTemplate.syncSendOrderly("partition-sort-message",message,String.valueOf(System.currentTimeMillis()));
        log.info("partition sort message:{}",message);
        return message;
    }
}
