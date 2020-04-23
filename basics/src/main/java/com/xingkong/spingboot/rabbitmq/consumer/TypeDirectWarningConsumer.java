package com.xingkong.spingboot.rabbitmq.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName: TypeDirectWarningConsumer
 * @Description: 交换器 type = "direct" 消费 queue_direct_1 路由键：warning
 * @Auther: fanxiaoping
 * @Date: 2018/9/9 15:07
 * @version: 1.0.0
 */
public class TypeDirectWarningConsumer {

    private static final String QUEUE_NAME = "queue_direct_2";

    private static final String IP_ADDRESS = "127.0.0.1";

    private static final Integer PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = new Address[]{new Address(IP_ADDRESS,PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection(addresses);
        Channel channel = connection.createChannel();
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties,byte[] body)throws IOException{
                System.out.println(new String(body));
                try{
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(QUEUE_NAME,consumer);
        TimeUnit.SECONDS.sleep(1);
        channel.close();
        connection.close();
    }
}
