package com.xingkong.spingboot.redis.redistemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;

/**
 * * @className: OrderService
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/9/25 0025 11:31
 **/
@Slf4j
@Service
public class OrderService {

    @Resource
    private RedisTemplate redisTemplate;

    public static final String ORDER_KEY = "order:";

    public void addOrder(){
        Random random = new Random();
        int keyId = random.nextInt(1000);
        String serialNo = UUID.randomUUID().toString();
        String key = ORDER_KEY + keyId;
        String value = "京东订单"+serialNo;
        redisTemplate.opsForValue().set(key,value);
        log.info("key:{},value:{}",key,value);
    }

    public String getOrderById(Integer keyId){
        return (String) redisTemplate.opsForValue().get(ORDER_KEY+keyId);
    }
}
