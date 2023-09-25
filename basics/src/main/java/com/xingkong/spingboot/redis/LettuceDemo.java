package com.xingkong.spingboot.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

/**
 * * @className: LettuceDemo
 * * @description: Lettuce
 * * @author: fan xiaoping
 * * @date: 2023/9/25 0025 10:17
 **/
public class LettuceDemo {

    public static void main(String[] args) {
        //1.使用构建器链式编程来builder我们RedisURI
        RedisURI uri = RedisURI.builder().redis("r-2ze4r1ddzycwxhkq9dpd.redis.rds.aliyuncs.com")
                .withPort(6379).withAuthentication("default","Rendui123456!").withDatabase(4).build();
        //2.创建连接客户端
        RedisClient redisClient = RedisClient.create(uri);
        StatefulRedisConnection connect = redisClient.connect();
        //3.创建操作的command，通过connect
        RedisCommands commands = connect.sync();
        //keys
        List keys = commands.keys("*");
        System.out.println(keys);
        //string
        commands.set("k5","hello-lettuce");
        System.out.println(commands.get("k5"));
        //...
        //4.各种关闭释放资源
        connect.close();
        redisClient.shutdown();
    }
}
