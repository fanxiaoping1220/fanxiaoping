package com.xingkong.spingboot.service.impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.xingkong.spingboot.commonutil.CheckUtils;
import com.xingkong.spingboot.commonutil.RedisUtil;
import com.xingkong.spingboot.entity.UserRedis;
import com.xingkong.spingboot.mapper.UserRedisMapper;
import com.xingkong.spingboot.redis.filter.BloomFilterInit;
import com.xingkong.spingboot.service.UserRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * * @className: UserRedisServiceImpl
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/11/17 0017 16:56
 **/
@Slf4j
@Service
public class UserRedisServiceImpl implements UserRedisService {

    public static final String KEY = "user:";

    @Autowired
    private UserRedisMapper userRedisMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CheckUtils checkUtils;

    //1.定义一个常量
    public static final int _1W = 10000;
    //2.定义我们guava布隆过滤器，初始容量
    public static final int SIZE = 100 * _1W;
    //3.误判率,它越小误判的个数也就越少(思考:是否可以是无限小？？没有误判率不是更好)
    public static final double FPP = 0.03;
    //4.创建guava布隆过滤器
    public static final BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),SIZE,FPP);

    @Transactional
    @Override
    public void addUser(UserRedis user) {
        user.setCreateTime(LocalDateTime.now());
        user.setDelFlag(false);
        userRedisMapper.insert(user);
        redisUtil.set(KEY+user.getId(),user);
    }

    @Override
    public UserRedis getUser(Long id) {
        String key = KEY+id;
        //redis
        UserRedis user = redisUtil.get(key);
        if(user == null){
            //mysql
            user = userRedisMapper.selectById(id);
            if(user != null){
                redisUtil.set(key,user);
            }
        }
        return user;
    }

    @Override
    public UserRedis findCustomerByIdWithBloomFilter(Integer customerId) {
        String key = KEY+customerId;
        //布隆过滤器check，无是绝对无，有是可能有
        if(!checkUtils.checkWithBloomFilter(BloomFilterInit.WHITE_LIST_CUSTOMER,key)){
            log.info("白名单无此客户,不可以访问{}",key);
            return null;
        }
        //redis
        UserRedis user = redisUtil.get(key);
        if(user == null){
            //mysql
            user = userRedisMapper.selectById(customerId);
            if(user != null){
                redisUtil.set(key,user);
            }
        }
        return user;
    }

    @Override
    public void guavaBloomFilter() {
        //1.先让bloomFilter加入100w白名单数据
        for (int i = 1; i <= SIZE; i++) {
            bloomFilter.put(i);
        }
        //2.故意取10w个不在合法范围内的数据,来进行误判率的演示
        ArrayList<Object> list = new ArrayList<>(10 * _1W);
        //3.验证
        for (int i = SIZE+1; i < SIZE + (10 * _1W); i++) {
            if(bloomFilter.mightContain(i)){
                log.info("被误判:{}",i);
                list.add(i);
            }
        }
        log.info("误判总数量:{}",list.size());

    }
}
