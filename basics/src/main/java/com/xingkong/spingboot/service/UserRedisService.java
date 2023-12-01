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

    /**
     * BloomFilter-->redis-->mysql
     * @param customerId
     * @return
     */
    UserRedis findCustomerByIdWithBloomFilter(Integer customerId);

    /**
     * google guava 布隆过滤器 case2
     */
    void guavaBloomFilter();

    /**
     * 获取下一个视频,已推荐过的不推荐
     * @param
     * @return
     */
    Integer getVideo();
}
