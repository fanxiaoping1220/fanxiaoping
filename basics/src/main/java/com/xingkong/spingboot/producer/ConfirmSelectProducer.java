package com.xingkong.spingboot.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.xingkong.spingboot.commonutil.ExchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName ConfirmSelectProducer
 * @Description 生产者确认 --- 发送方确认机制
 * @Author fanxiaoping
 * @Date 2018/10/11 10:25
 * @Version 1.0.0
 **/
public class ConfirmSelectProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Producer producer = new Producer();
        Channel channel = producer.basic();
        channel.exchangeDeclare("confirmExchange",ExchangeType.EXCHANGE_TYPE_FANOUT.getName(),true,false,null);
        channel.queueDeclare("confirmQueue",true,false,false,null);
        channel.queueBind("confirmQueue","confirmExchange","");
        try{
            /**
             * 将信道设置为publisher confirm
             */
            channel.confirmSelect();
            /**
             * 之后正常发送消息
             */
            String message = "hello confirm select producer rabbitMq";
            channel.basicPublish("confirmExchange","confirm",MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            if(!channel.waitForConfirms()){
                System.out.println("send message failed");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
