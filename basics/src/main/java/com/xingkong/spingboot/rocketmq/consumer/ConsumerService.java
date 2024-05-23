package com.xingkong.spingboot.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * * @className: ConsumerService
 * * @description:消息消费者
 * * @author: fan xiaoping
 * * @date: 2024/5/23 0023 16:28
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "test-topic",consumerGroup = "my-consumer_test_topic")
public class ConsumerService implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        log.info("接收消息 msg:{}",msg);
    }
}
