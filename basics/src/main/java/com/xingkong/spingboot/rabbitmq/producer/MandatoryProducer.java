package com.xingkong.spingboot.rabbitmq.producer;

import com.rabbitmq.client.*;
import com.xingkong.spingboot.commonutil.ExchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName MandatoryProducer
 * @Description 发送消息 channel.basicPublish
 * @Author fanxiaoping
 * @Date 2018/9/26 15:33
 * @Version 1.0.0
 **/
public class MandatoryProducer {

    /**
     * 交换器名称
     */
    private static final String EXCHANGE_NAME = "exchangeName";

    /**
     * 交换器名称
     */
    private static final String QUEUE_NAME = "queueName";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String message = "mandatory test";
        channel.exchangeDeclare(EXCHANGE_NAME, ExchangeType.EXCHANGE_TYPE_DIRECT.getName(), true, false, null);
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "mandatory");
        channel.basicPublish(EXCHANGE_NAME, "", true, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("basic return返回的结果是：" + message);
            }
        });

    }
}
