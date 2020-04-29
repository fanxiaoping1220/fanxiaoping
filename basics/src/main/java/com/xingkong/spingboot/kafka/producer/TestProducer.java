package com.xingkong.spingboot.kafka.producer;

import com.xingkong.spingboot.kafka.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * * @className: a
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020-04-23  15:11
 **/
@RestController
@RequestMapping(value = "/kafka")
public class TestProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @GetMapping(value = "/sendMessage")
    public void sendMessage() {
        kafkaTemplate.send("test", "test01");
    }

    @GetMapping(value = "/sendModel")
    public void sendModel() {
        Test test = new Test("test", 1233);
        kafkaTemplate.send("test-1", test);
    }
}
