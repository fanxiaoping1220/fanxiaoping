package com.xingkong.spingboot.service.impl;

import com.xingkong.spingboot.commonutil.RedisUtil;
import com.xingkong.spingboot.entity.UserRedis;
import com.xingkong.spingboot.mapper.UserRedisMapper;
import com.xingkong.spingboot.service.UserRedisService;
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
@Service
public class UserRedisServiceImpl implements UserRedisService {

    public static final String KEY = "user:";

    @Autowired
    private UserRedisMapper userRedisMapper;

    @Autowired
    private RedisUtil redisUtil;

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
}
