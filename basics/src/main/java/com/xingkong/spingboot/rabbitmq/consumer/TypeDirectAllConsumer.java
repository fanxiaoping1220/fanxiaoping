package com.xingkong.spingboot.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: TypeDirectAllConsumer
 * @Description: 交换器 type = "direct" 消费 queue_direct_1 路由键：warning info debug
 * @Auther: fanxiaoping
 * @Date: 2018/9/9 14:01
 * @version: 1.0.0
 */
public class TypeDirectAllConsumer {

    private static final String QUEUE_NAME = "queue_direct_1";

    private static final String IP_ADDRESS = "127.0.0.1";

    private static final Integer PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = new Address[]{new Address(IP_ADDRESS, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setPassword("guest");
        factory.setUsername("guest");
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(body);
                System.out.println("recv message: " + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, consumer);
        /**
         * 等待回调函数执行完毕之后，关闭资源
         */
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }


}
