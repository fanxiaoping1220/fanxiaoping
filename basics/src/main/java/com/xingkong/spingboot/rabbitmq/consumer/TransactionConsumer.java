package com.xingkong.spingboot.rabbitmq.consumer;

import com.rabbitmq.client.*;
import com.xingkong.spingboot.rabbitmq.producer.Producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName TransactionConsumer
 * @Description 带事务的消息-----消费者
 * @Author fanxiaoping
 * @Date 2018/10/10 16:20
 * @Version 1.0.0
 **/
public class TransactionConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Producer producer = new Producer();
        Channel channel = producer.basic();
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(body);
                System.out.println(new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume("transactionQueue", consumer);
        channel.close();
    }
}
