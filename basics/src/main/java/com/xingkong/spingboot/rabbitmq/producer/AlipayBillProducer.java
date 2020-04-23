package com.xingkong.spingboot.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: AlipayBillProducer
 * @description: 支付宝账单消息生产者
 * @author: 范小平
 * @date: 2019-09-10 16:43
 * @version: 1.0.0
 */
@Component
public class AlipayBillProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue alipayBillQueue(){
        return new Queue("alipayBill");
    }

    /**
     * 发送支付宝账单数据
     * 将InputStream读取完的数据发送给队列mq
     * 消费队列mq对数据进行存储
     * @param data
     */
    public void sendAlipayBill(List<List<String[]>> data){
        amqpTemplate.convertAndSend("alipayBill",data);
    }

}