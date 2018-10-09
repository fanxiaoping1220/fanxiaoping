package com.xingkong.spingboot.producer;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xingkong.spingboot.commonutil.Consts;
import com.xingkong.spingboot.commonutil.ExchangeType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName TTLProducer
 * @Description 设置过期时间
 * @Author fanxiaoping
 * @Date 2018/10/9 17:11
 * @Version 1.0.0
 **/
public class TTLProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Consts.IP_ADDRESS);
        factory.setPort(Consts.PORT);
        factory.setUsername(Consts.USER_NAME);
        factory.setPassword(Consts.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("TTLExchange",ExchangeType.EXCHANGE_TYPE_FANOUT.getName(),true,false,null);
        /**
         * 1.创建一个过期时间为30分钟的队列
         */
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-expires",1800000);
        channel.queueDeclare("TTLQueue",false,false,false,arguments);
        channel.queueBind("TTLQueue","TTLExchange","routingKey");
        String message = " Hello TTL RabbitMq";
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        //持久化消息
        builder.deliveryMode(2);
        //设置TTL = 6000ms
        builder.expiration("6000");
        AMQP.BasicProperties properties = builder.build();
        /**
         * 2.发送一条消息过期时间为6000ms的
         */
        channel.basicPublish("TTLExchange","",properties,message.getBytes());
        channel.close();
        connection.close();
    }
}
