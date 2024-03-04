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

    /**
     * 扣减库存,一次卖一个
     * lock/synchronized
     * @param
     * @return
     */
    String sale();

    /**
     * 3.1版
     * 扣减库存,一次卖-个
     * 采用redis锁
     * @return
     */
    String sale2();

    /**
     * 3.2版
     * 3.1的优化版
     * 扣减库存,一次卖-个
     * 采用redis锁
     * @return
     */
    String sale3();

    /**
     * 4.0版
     * 3.2版的优化版
     * 扣减库存,一次卖-个
     * 采用redis锁
     * @return
     */
    String sale4();

    /**
     * 5.0版
     * 4.0版的改进版
     * 扣减库存,一次卖-个
     * 采用redis锁
     * @return
     */
    String sale5();

    /**
     * 6.0版
     * 5.0版的改进版
     * 扣减库存,一次卖-个
     * 采用redis锁+lua脚本
     * @return
     */
    String sale6();

    /**
     * 7.0版
     * 6.0版的改进版
     * 扣减库存,一次卖-个
     * 采用redis锁+lua脚本 hset
     * @return
     */
    String sale7();

    /**
     * 7.1版
     * 7.0版的优化版
     * 扣减库存,一次卖-个
     * 采用redis锁+lua脚本 hset+工厂模式
     * @return
     */
    String sale8();

    /**
     * 7.2版
     * 7.1版的优化版
     * 采用redis锁+lua脚本 hset+工厂模式+可重入性
     * 测试7.1版的可重入性
     * @return
     */
    String sale9();

    /**
     * 8.0版
     * 7.2版的优化版
     * 加自动续期
     * 采用redis锁+lua脚本 hset+工厂模式+可重入性+自动续期
     * @return
     */
    String sale10();

    /**
     * 9.0版
     * 8.0版的优化版
     * 采用redisson分布式锁
     * @return
     */
    String saleByRedisson();

    /**
     * 9.1版
     * 9.0版的优化版
     * 采用redisson分布式锁
     * @return
     */
    String saleByRedisson2();
}
