package com.xingkong.spingboot.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.xingkong.spingboot.commonUtil.ExchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: ExchangeTypeDirectProducer
 * @Description: 交换器 type = "direct"
 * @Auther: fanxiaoping
 * @Date: 2018/9/9 14:03
 * @version: 1.0.0
 */
public class ExchangeTypeDirectProducer {

    private static final String EXCHANGE_NAME = "exchange_direct";

    private static final Integer PORT = 5672;

    private static final String IP_ADDRESS = "127.0.0.1";

    private static final String QUEUE_NAME_1 = "queue_direct_1";

    private static final String QUEUE_NAME_2 = "queue_direct_2";

    private static final String ROUTING_KEY_WARNING = "routingKey_warning";

    private static final String ROUTING_KEY_INFO = "routingKey_info";

    private static final String ROUTING_KEY_DEBUG = "routingKey_debug";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,ExchangeType.EXCHANGE_TYPE_DIRECT.getName(),true,false,null);
        channel.queueDeclare(QUEUE_NAME_1,true,false,false,null);
        channel.queueDeclare(QUEUE_NAME_2,true,false,false,null);
        channel.queueBind(QUEUE_NAME_1,EXCHANGE_NAME,ROUTING_KEY_INFO);
        channel.queueBind(QUEUE_NAME_1,EXCHANGE_NAME,ROUTING_KEY_WARNING);
        channel.queueBind(QUEUE_NAME_1,EXCHANGE_NAME,ROUTING_KEY_DEBUG);
        /**
         * 参数1.队列，2.交换器，3.路由键
         */
        channel.queueBind(QUEUE_NAME_2,EXCHANGE_NAME,ROUTING_KEY_WARNING);
        String message = "Hello Exchange type direct"+ROUTING_KEY_WARNING;
        String message1 = "Hello Exchange type direct"+ROUTING_KEY_INFO;
        String message2 = "Hello Exchange type direct"+ROUTING_KEY_DEBUG;
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_WARNING, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_DEBUG,MessageProperties.PERSISTENT_TEXT_PLAIN,message2.getBytes());
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY_INFO,MessageProperties.PERSISTENT_TEXT_PLAIN,message1.getBytes());
        channel.close();
        connection.close();
    }
}
