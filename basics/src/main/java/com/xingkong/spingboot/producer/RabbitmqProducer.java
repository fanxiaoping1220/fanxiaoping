package com.xingkong.spingboot.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RabbitmqProducer
 * @Description 生产者
 * @Author fanxiaoping
 * @Date 2018/9/4 14:44
 * @Version 1.0.0
 **/
public class RabbitmqProducer {

    /**
     * 交换机名称
     */
    private static final String EXCHANGE_NAME = "exchange_demo";

    /**
     *路由key
     */
    private static final String ROUTING_KEY = "routingkey_demo";

    /**
     * 队列名称
     */
    private static final String QUEUE_NAME = "queue_demo";

    /**
     * IP地址
     */
    private static final String IP_ADDRESS = "127.0.0.1";

    /**
     * 默认端口号
     */
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        /**
         * 设置地址
         */
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("root");
        factory.setPassword("root123");
        /**
         * 创建连接
         */
        Connection connection = factory.newConnection();
        /**
         * 创建通道
         */
        Channel channel = connection.createChannel();
        /**
         * 创建一个type="direct",持久化的,非自动删除的交换机
         */
        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
        /**
         * 创建一个持久化,非排他的,非自动删除的队列
         */
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        /**
         * 将交换机与队列通过路由键绑定
         */
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        /**
         * 发送一条持久化消息
         */
        String message = " Hello Rabbitmq";
        channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        /**
         * 关闭资源
         */
        channel.close();
        connection.close();
    }
}
