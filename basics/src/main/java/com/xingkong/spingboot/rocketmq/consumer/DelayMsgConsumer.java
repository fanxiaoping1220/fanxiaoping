package com.xingkong.spingboot.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @className: DelayMsgConsumer
 * @author: fanxiaoping
 * @date: 2025/3/28
 * @description:
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "delay-message",consumerGroup = "delay-message-topic")
public class DelayMsgConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("消费时间:{}", LocalDateTime.now());
        log.info("延迟消息消费者接收到消息:{}",message);
    }
}
