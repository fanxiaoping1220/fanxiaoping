package com.xingkong.spingboot.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.xingkong.spingboot.commonutil.ExchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName ExchangeBindProducer
 * @Description ExchangeBind source ---> destination 绑定
 * @Author fanxiaoping
 * @Date 2018/9/19 19:50
 * @Version 1.0.0
 **/
public class ExchangeBindProducer {

    private static final String EXCHANGE_NAME_1 = "source";

    private static final String EXCHANGE_NAME_2 = "destination";

    private static final Integer PORT = 5672;

    private static final String IP_ADDRESS = "127.0.0.1";

    private static final String QUEUE_NAME = "queue";

    private static final String ROUTING_KEY = "routingKey";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME_1,ExchangeType.EXCHANGE_TYPE_DIRECT.getName(),true,false,null);
        channel.exchangeDeclare(EXCHANGE_NAME_2,ExchangeType.EXCHANGE_TYPE_FANOUT.getName(),true,false,null);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME_2,"");
        channel.exchangeBind(EXCHANGE_NAME_2,EXCHANGE_NAME_1,ROUTING_KEY);
        String message = "Hello ExchangeBind source ---> destination 绑定";
        channel.basicPublish(EXCHANGE_NAME_1,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.close();
        connection.close();
    }

}
