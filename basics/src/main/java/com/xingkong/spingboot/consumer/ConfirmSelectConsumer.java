package com.xingkong.spingboot.consumer;

import com.rabbitmq.client.*;
import com.xingkong.spingboot.producer.Producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName ConfirmSelectConsumer
 * @Description 带发送方确认的消息 ----消费者
 * @Author fanxiaoping
 * @Date 2018/10/11 10:36
 * @Version 1.0.0
 **/
public class ConfirmSelectConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Producer producer = new Producer();
        Channel channel = producer.basic();
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(body);
                System.out.println(new String(body));
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume("confirmQueue",consumer);
    }
}
