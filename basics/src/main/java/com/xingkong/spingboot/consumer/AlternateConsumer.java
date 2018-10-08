package com.xingkong.spingboot.consumer;

import com.rabbitmq.client.*;
import com.xingkong.spingboot.commonutil.Consts;

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
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPort(Consts.PORT);
        factory.setHost(Consts.IP_ADDRESS);
        factory.setUsername(Consts.USER_NAME);
        factory.setPassword(Consts.PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
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
        connection.close();
    }
}
