package com.xingkong.spingboot.commonutil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * * @className: CheckUtils
 * * @description: 布隆过滤器白名单检验
 * * @author: fan xiaoping
 * * @date: 2023/11/29 0029 17:04
 **/
@Slf4j
@Component
public class CheckUtils {

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 检验白名单下的某个key是否存在
     * @param checkItem 白名单
     * @param key key
     * @return true false
     */
    public Boolean checkWithBloomFilter(String checkItem,String key){
        int hashCode = Math.abs(key.hashCode());
        long index = (long) (hashCode % Math.pow(2,32));
        boolean existOk = redisUtil.bitGet(checkItem, index);
        log.info("---->key:{},对应坑位下标index:{},是否存在:{}",key,index,existOk);
        return existOk;
    }
}
