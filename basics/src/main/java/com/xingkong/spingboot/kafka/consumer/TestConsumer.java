package com.xingkong.spingboot.kafka.consumer;

import com.alibaba.fastjson.JSON;
import com.xingkong.spingboot.kafka.model.Test;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * * @className: a
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020-04-23  15:11
 **/
@Component
public class TestConsumer {

    /**
     * 字符串
     * @param data
     */
    @KafkaListener(topics = "test")
    public void consumerTest(String data){
        System.out.println(data);
    }

    /**
     * model
     * @param data
     */
    @KafkaListener(topics = "test-1")
    public void consumerModelTest(String data){
        Test test = JSON.parseObject(data, Test.class);
        System.out.println(test.toString());
    }
}
