package com.xingkong.spingboot.rabbitmq.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.xingkong.spingboot.commonutil.Consts;
import com.xingkong.spingboot.commonutil.ExchangeType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName AlternateExchange
 * @Description 备份交换器
 * @Author fanxiaoping
 * @Date 2018/10/8 11:12
 * @Version 1.0.0
 **/
public class AlternateExchange {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Consts.IP_ADDRESS);
        factory.setPort(Consts.PORT);
        factory.setUsername(Consts.USER_NAME);
        factory.setPassword(Consts.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("alternate-exchange", "myAe");
        channel.exchangeDeclare("normalExchange", ExchangeType.EXCHANGE_TYPE_DIRECT.getName(), true, false, arguments);
        channel.exchangeDeclare("myAe", ExchangeType.EXCHANGE_TYPE_FANOUT.getName(), true, false, null);
        channel.queueDeclare("normalQueue", true, false, false, null);
        channel.queueDeclare("unroutedQueue", true, false, false, null);
        channel.queueBind("normalQueue", "normalExchange", "normalKey");
        channel.queueBind("unroutedQueue", "myAe", "");
        String message = "Hello Alternate Exchange ";
        channel.basicPublish("normalExchange", "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.basicPublish("normalExchange", "normalKey", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
        connection.close();
    }
}
