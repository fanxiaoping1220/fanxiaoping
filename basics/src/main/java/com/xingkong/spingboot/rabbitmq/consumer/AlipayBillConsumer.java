package com.xingkong.spingboot.rabbitmq.consumer;

import com.xingkong.spingboot.service.bill.service.impl.AlipayBillServiceImpl;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: AlipayBillConsumer
 * @description: 支付宝账单消息消费者
 * @author: 范小平
 * @date: 2019-09-10 17:38
 * @version: 1.0.0
 */
@Component
public class AlipayBillConsumer {

    @Autowired
    private AlipayBillServiceImpl alipayBillService;

    /**
     * 消费队列 alipayBill
     * 将数据保存到数据库相应的表
     * 支付宝账单明细表
     * 支付宝账单汇总表
     */
    @RabbitListener(queues = "alipayBill")
    @RabbitHandler
    public void saveData(List<List<String[]>> data) {
        alipayBillService.saveData(data);
    }
}