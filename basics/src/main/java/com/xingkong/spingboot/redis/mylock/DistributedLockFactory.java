package com.xingkong.spingboot.redis.mylock;

import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * * @className: DistributedLockFactory
 * * @description:采用工程模式的分布式锁
 * * @author: fan xiaoping
 * * @date: 2024/2/20 0020 14:49
 **/
@Component
public class DistributedLockFactory {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private String lockName;

    private String uuid;

    public DistributedLockFactory() {
        this.uuid = IdUtil.simpleUUID();
    }

    public Lock getDistributedLock(String lockType) {
        if(StringUtils.isBlank(lockType)){
            return null;
        }
        if("redis".equalsIgnoreCase(lockType)){
            //redis分布式锁
            this.lockName = "zzyyRedisLock";
            return  new RedisDistributedLock(stringRedisTemplate,lockName,uuid);
        }else if("zookeeper".equalsIgnoreCase(lockType)) {
            //zookeeper分布式锁
            return null;
        }else if ("mysql".equalsIgnoreCase(lockType)){
            //mysql分布式锁
            return null;
        }
        return null;
    }
}
