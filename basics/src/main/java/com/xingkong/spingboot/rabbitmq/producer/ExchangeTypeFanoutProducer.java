package com.xingkong.spingboot.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.xingkong.spingboot.commonutil.ExchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: ExchangeTypeFanoutProducer
 * @Description: 交换器 type = "fanout"
 * @Auther: fanxiaoping
 * @Date: 2018/9/9 16:36
 * @version: 1.0.0
 */
public class ExchangeTypeFanoutProducer {

    private static final String EXCHANGE_NAME = "exchange_fanout";

    private static final String IP_ADDRESS = "127.0.0.1";

    private static final Integer PORT = 5672;

    private static final String QUEUE_NAME = "queue_fanout";

    private static final String ROUTING_KEY = "routingKey_fanout";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(PORT);
        factory.setHost(IP_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.EXCHANGE_TYPE_FANOUT.getName(), true, false, null);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        String message = "Hello Rabbitmq Exchange Type fanout";
        channel.basicPublish(EXCHANGE_NAME, "111", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
        connection.close();
    }
}
