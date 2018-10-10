package com.xingkong.spingboot.producer;

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
 * @ClassName DLXProducer
 * @Description 死信队列 设置TTL DLX的队列
 * @Author fanxiaoping
 * @Date 2018/10/10 10:14
 * @Version 1.0.0
 **/
public class DLXProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Consts.IP_ADDRESS);
        factory.setPort(Consts.PORT);
        factory.setUsername(Consts.USER_NAME);
        factory.setPassword(Consts.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare("exchange.dlx",ExchangeType.EXCHANGE_TYPE_DIRECT.getName(),true);
        channel.exchangeDeclare("exchange.normal",ExchangeType.EXCHANGE_TYPE_FANOUT.getName(),true);
        Map<String,Object> map = new HashMap<>();
        map.put("x-message-ttl",10000);
        map.put("x-dead-letter-exchange","exchange.dlx");
        map.put("x-dead-letter-routing-key","routingKey");
        channel.queueDeclare("queue.normal",true,false,false,map);
        channel.queueBind("queue.normal","exchange.normal","");
        channel.queueDeclare("queue.dlx",true,false,false,null);
        channel.queueBind("queue.dlx","exchange.dlx","routingKey");
        String message = "hello queue TTL DLX";
        channel.basicPublish("exchange.normal","rk",MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.close();
        connection.close();
    }
}