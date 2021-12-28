package com.xingkong.spingboot.rabbitmq.consumer.springboot;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * * @className: ExerciseMqAbnormalConsumer
 * * @description: 处理消息消费异常
 * 当程序出现异常时，重试3次，如果失败，将通过死信队列存入到进行二次处理数据库，相当于当异常的数据进行保存，方便后期可以人工进行处理
 * * @author: fan xiaoping
 * * @date: 2021/12/28 0028 12:19
 **/
@Slf4j
@Component
public class ExerciseMqAbnormalConsumer {

//    @RabbitListener(queues = "dead.queue")
//    @RabbitHandler
//    public void abnormalConsumer(String data){
//        //制造异常重试3次进入死信队列
//        System.out.println(1/0);
//        System.out.println(data);
//    }

    @RabbitListener(queues = "dead.queue")
    @RabbitHandler
    public void abnormalConsumer(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String msg = new String(message.getBody());
        log.info("消息为:"+msg);
        try{
            System.out.println(1/0);
            channel.basicAck(deliveryTag,false);
        }catch (Exception e){
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            //重试次数
            Integer retryCount;
            String mapKey = "retry-count";
            if(!headers.containsKey(mapKey)){
                retryCount = 0;
            }else {
                retryCount = (Integer) headers.get(mapKey);
            }
            if(retryCount++ < 3){
                log.info("已经重试"+retryCount+"次");
                headers.put("retry-count",retryCount);
                //当消息回滚到消息队列时，这条消息不会回到队列尾部，而是仍是在队列头部。
                //这时消费者会立马又接收到这条消息进行处理，接着抛出异常，进行 回滚，如此反复进行
                //而比较理想的方式是，出现异常时，消息到达消息队列尾部，这样既保证消息不回丢失，又保证了正常业务的进行。
                //因此我们采取的解决方案是，将消息进行应答。
                //这时消息队列会删除该消息，同时我们再次发送该消息 到消息队列，这时就实现了错误消息进行消息队列尾部的方案
                channel.basicAck(deliveryTag,false);
                //重新发送消息
                AMQP.BasicProperties build = new AMQP.BasicProperties().builder().headers(headers).contentType("application/json").build();
                channel.basicPublish(message.getMessageProperties().getReceivedExchange(),message.getMessageProperties().getReceivedRoutingKey(),build,message.getBody());
            }else {
                log.info("现在重试次数为：" + retryCount);
                channel.basicNack(deliveryTag,false,false);
            }
        }
    }


    @RabbitListener(queues = "handle.queue")
    @RabbitHandler
    public void deadTestReceiver(Message message, Channel channel) throws IOException {
        log.info("将重试3次异常的消息存入数据库!");
        log.info("消息将放入死信队列:"+new String(message.getBody()));
        channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
    }
}
