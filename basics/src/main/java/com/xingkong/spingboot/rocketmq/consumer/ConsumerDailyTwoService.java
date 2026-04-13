package com.xingkong.spingboot.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * * @className: ConsumerDailyTwoService
 * * @description:一个消息多个消费者消息分别为ConsumerDailyService,ConsumerDailyTwoService
 * * @author: fan xiaoping
 * * @date: 2024/5/24 0024 15:02
 **/
@Slf4j
@Component
@RocketMQMessageListener(topic = "test-daily-topic",consumerGroup = "my-consumer_test-topic-two",messageModel = MessageModel.BROADCASTING)
public class ConsumerDailyTwoService implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("tow consumer message:{}",message);
    }
}
