package com.xingkong.spingboot.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.xingkong.spingboot.commonutil.ExchangeType;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName TransactionProducer
 * @Description 生产者确实 ---- 事务确认机制
 * @Author fanxiaoping
 * @Date 2018/10/10 16:10
 * @Version 1.0.0
 **/
public class TransactionProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Producer producer = new Producer();
        Channel channel = producer.basic();
        channel.exchangeDeclare("transactionExchange",ExchangeType.EXCHANGE_TYPE_FANOUT.getName(),true,false,null);
        channel.queueDeclare("transactionQueue",true,false,false,null);
        channel.queueBind("transactionQueue","transactionExchange","");
        try {
            channel.txSelect();
            String message = "hello transaction rabbitMq producer";
            channel.basicPublish("transactionExchange","a",MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            channel.txCommit();
        }catch (Exception e){
            e.printStackTrace();
            channel.txRollback();
        }

    }
}
