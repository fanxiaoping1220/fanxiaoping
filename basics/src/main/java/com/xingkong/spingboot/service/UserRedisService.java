package com.xingkong.spingboot.service;

import com.xingkong.spingboot.entity.UserRedis;

/**
 * * @className: UserRedisService
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/11/17 0017 16:56
 **/
public interface UserRedisService {

    /**
     * 添加user
     * @param user
     */
    void addUser(UserRedis user);

    /**
     * 查询
     * @param id
     * @return
     */
    UserRedis getUser(Long id);
}
