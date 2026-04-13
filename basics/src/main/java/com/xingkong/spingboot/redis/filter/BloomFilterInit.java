package com.xingkong.spingboot.redis.filter;

import com.xingkong.spingboot.commonutil.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * * @className: BloomFilterInit
 * * @description: 布隆过滤器白名单初始化工具类，一开始就设置一部分数据为白名单所有,
 * * 白名单业务默认规定: 布隆过滤器有,redis是极大可能有
 * * 白名单: whitelistCustomer
 * * @author: fan xiaoping
 * * @date: 2023/11/29 0029 16:38
 **/
@Slf4j
@Component
public class BloomFilterInit {

    /**
     * 白名单key
     */
    public static final String WHITE_LIST_CUSTOMER = "whitelistCustomer";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 初始化白名单数据
     */
    @PostConstruct
    public void inti(){
        //1.白名单客户加载到布隆过滤器
        String key = "user:5";
        //2.计算hashValue,由于存在计算出来负数的可能，我们取绝对值
        int hashValue = Math.abs(key.hashCode());
        //3.t通过hashValue和2的32次方取余后，获得对应的下标坑位
        long index = (long) (hashValue % Math.pow(2,32));
        log.info(key+" 对应的坑位index:{}",index);
        //4.设置redis里面的bitmap对应类型白名单:whitelistCustomer的坑位，将改值设置为1
        redisUtil.bitSet(WHITE_LIST_CUSTOMER,index,true);
        String key2 = "user:6";
        int hashCode = Math.abs(key2.hashCode());
        long index2 = (long) (hashCode % Math.pow(2,32));
        log.info(key2+" 对应的坑位index2:{}",index2);
        redisUtil.bitSet(WHITE_LIST_CUSTOMER,index2,true);
    }
}
