package com.xingkong.spingboot.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.xingkong.spingboot.commonutil.Consts;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description
 * @Author fanxiaoping
 * @Date 2018/10/10 14:47
 * @Version 1.0.0
 **/
public class Producer {

    public Channel basic() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Consts.IP_ADDRESS);
        factory.setPort(Consts.PORT);
        factory.setUsername(Consts.USER_NAME);
        factory.setPassword(Consts.PASSWORD);
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }
}
