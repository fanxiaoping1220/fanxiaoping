package com.xingkong.spingboot.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName ExchangeBindConsumer
 * @Description 交换器绑定交换器 ExchangeBind source ---> destination 绑定
 * @Author fanxiaoping
 * @Date 2018/9/19 19:49
 * @Version 1.0.0
 **/
public class ExchangeBindConsumer {

    /**
     * 队列名称
     */
    private static final String QUEUE_NAME = "queue";

    /**
     * IP地址
     */
    private static final String IP_ADDRESS = "127.0.0.1";

    /**
     * 默认端口号
     */
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = new Address[]{new Address(IP_ADDRESS,PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        /**
         * 创建连接
         */
        Connection connection = factory.newConnection(addresses);
        /**
         * 创建信到
         */
        final Channel channel = connection.createChannel();
        /**
         * 设置客户端最多接收未被ack的消息个数
         */
        channel.basicQos(64);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,AMQP.BasicProperties properties,byte[] body)throws IOException {
                System.out.println(body);
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
        /**
         * 等待回调函数执行完毕之后，关闭资源
         */
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }
}
