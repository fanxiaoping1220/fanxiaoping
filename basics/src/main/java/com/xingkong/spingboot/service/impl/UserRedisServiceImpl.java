package com.xingkong.spingboot.service.impl;

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
}
