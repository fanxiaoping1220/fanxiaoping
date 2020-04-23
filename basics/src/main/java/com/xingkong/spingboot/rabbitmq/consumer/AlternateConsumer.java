package com.xingkong.spingboot.rabbitmq.consumer;

import com.rabbitmq.client.*;
import com.xingkong.spingboot.rabbitmq.producer.Producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName AlternateConsumer
 * @Description 备份交换器
 * @Author fanxiaoping
 * @Date 2018/10/8 12:05
 * @Version 1.0.0
 **/
public class AlternateConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Producer producer = new Producer();
        Channel channel = producer.basic();
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume("normalQueue",consumer);
        channel.basicConsume("unroutedQueue",consumer);
        channel.close();
    }
}
