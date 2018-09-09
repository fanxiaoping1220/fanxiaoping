package com.xingkong.spingboot.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: ExchangeTypeTopicProducer
 * @Description: Exchange type = "topic";
 * @Auther: fanxiaoping
 * @Date: 2018/9/9 17:28
 * @version: 1.0.0
 */
public class ExchangeTypeTopicProducer {

    private static final String EXCHANGE_NAME = "Exchange_topic";

    private static final String QUEUE_NAME_1 = "queue_topic_1";

    private static final String QUEUE_NAME_2 = "queue_topic_2";

    private static final String ROUTING_KEY_1 = "routingKey_topic";

    private static final String ROUTING_KEY_2 = "routingKey_demo";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic",true,false,null);
        channel.queueDeclare(QUEUE_NAME_1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME_2,true,false,false,null);
        channel.queueBind(QUEUE_NAME_1,EXCHANGE_NAME,"routingKey_#");
        channel.queueBind(QUEUE_NAME_2,EXCHANGE_NAME,"routingKey_*");
        String message = "hello rabbitmq exchange type topic "+ROUTING_KEY_1;
        String message2 = "hello rabbitmq exchange type topic "+ROUTING_KEY_2;
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_1, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_2, MessageProperties.PERSISTENT_TEXT_PLAIN,message2.getBytes());
        channel.close();
        connection.close();
    }
}
