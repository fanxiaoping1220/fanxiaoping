package com.xingkong.spingboot.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitmqPullConsumer
 * @Description 消费者 pull 拉模式 一次只能消费一个消息载体
 * @Author fanxiaoping
 * @Date 2018/9/25 17:19
 * @Version 1.0.0
 **/
public class RabbitmqPullConsumer {

    private static final String QUEUE_NAME = "queue_demo";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        GetResponse response = channel.basicGet(QUEUE_NAME,false);
        System.out.println(new String(response.getBody()));
        channel.basicAck(response.getEnvelope().getDeliveryTag(),false);
        channel.close();
        connection.close();
    }
}
