package com.xingkong.spingboot.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * * @className: OneWayMsgConsumer
 * * @description:单向消息消费者
 * * @author: fan xiaoping
 * * @date: 2024/7/11 0011 16:57
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "one-way-message",consumerGroup = "one-way-message-topic")
public class OneWayMsgConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("单向消息消费者接收到消息：{}",message);
        System.out.println("message:"+message);
    }
}
