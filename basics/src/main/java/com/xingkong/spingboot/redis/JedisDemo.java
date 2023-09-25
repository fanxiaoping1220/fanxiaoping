package com.xingkong.spingboot.redis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * * @className: JedisDemo
 * * @description: jedis
 * * @author: fan xiaoping
 * * @date: 2023/9/22 0022 16:31
 **/
public class JedisDemo {

    public static void main(String[] args) {
        //1.connection 获得，通过指定ip和端口号
        Jedis jedis = new Jedis("r-2ze4r1ddzycwxhkq9dpd.redis.rds.aliyuncs.com",6379);
        //2.指定访问redis的密码
        jedis.auth("Rendui123456!");
        //3.获得jedis客户端，可以向jdbc一样，访问我们的redis
        System.out.println(jedis.ping());
        //指定db4
        jedis.select(4);

        //keys
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        //string
        jedis.set("k3","hello-jedis");
        System.out.println(jedis.get("k3"));
        System.out.println(jedis.ttl("k3"));
        jedis.expire("k3",20L);

        //list
        jedis.lpush("list","11","22","33");
        List<String> list = jedis.lrange("list", 0, -1);
        list.forEach(s -> {
            System.out.println(s);
        });
    }
}
