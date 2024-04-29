package com.xingkong.spingboot.service.impl;

import cn.hutool.core.util.IdUtil;
import com.xingkong.spingboot.commonutil.RedisUtil;
import com.xingkong.spingboot.service.RedPackageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Random;

/**
 * * @className: RedPackageServiceImpl
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2024/4/29 0029 16:51
 **/
@Slf4j
@Service
public class RedPackageServiceImpl implements RedPackageService {

    @Autowired
    private RedisUtil redisUtil;

    public static final String RED_PACKAGE_KEY = "redPackage:";

    public static final String RED_PACKAGE_CONSUMER_KEY = "redPackage:consumer:";

    @Override
    public String sendRedPackage(int totalMoney, int redPackageNumber) {
        //1.拆红包,将总金额totalMoney拆分为redPackageNumber个子红包
        //拆分红包算法通过获得的多个子红包数组
        Integer[] splitRedPackages= splitRedPackage(totalMoney, redPackageNumber);
        //2.发红包并保存进list结构而且设置过期时间
        String key = RED_PACKAGE_KEY + IdUtil.simpleUUID();
        redisUtil.lSet(key,24*60*60,splitRedPackages);
        //3.发红包ok，返回前台显示
        return key+"\t"+ Arrays.toString(Arrays.stream(splitRedPackages).mapToInt(Integer::intValue).toArray());
    }

    @Override
    public String robRedPackage(String redPackageKey, String userId) {
        //1.验证某个用户是否已经抢过红包，不可以多抢
        Object redPackage = redisUtil.hget(RED_PACKAGE_CONSUMER_KEY + redPackageKey, userId);
        //2.没有抢过可以去抢红包,否则返回-2表示该用户已抢过
        if(redPackage == null){
            //2.1从大红包(list)里面出队一个作为该客户抢的红包,抢到了一个红包
            Object partRedPackage = redisUtil.lGet(RED_PACKAGE_KEY + redPackageKey);
            if(partRedPackage != null){
                //2.2抢到红包后需要记录hash结构,表示谁抢到了多少钱的某个子红包
                redisUtil.hset(RED_PACKAGE_CONSUMER_KEY+redPackageKey,userId,partRedPackage);
                log.info("用户:{},抢到的红包:{}",userId,partRedPackage);
                //TODO 后续异步进mysql或者mq进一步做统计处理,每一年你发出多少红包,抢到了多少红包,年度总结
                return partRedPackage.toString();
            }
            //抢完了
            return "errorCode:-1,message:红包抢完了";
        }
        //3.某个用户抢过了，不可以作弊多抢
        return "errorCode:-2,message:"+userId+"\t"+"您已经抢过红包了,不能重新抢,已抢到的红包金额为:"+redPackage;
    }

    /**
     * 拆红包的算法--->二倍均值算法
     * 随机(0,(剩余红包金额/未被抢的剩余红包个数N) * 2)
     * @param totalMoney
     * @param redPackageNumber
     * @return
     */
    private Integer[] splitRedPackage(int totalMoney, int redPackageNumber) {
        Integer[] redPackageNumbers = new Integer[redPackageNumber];
        //已经被抢夺的红包金额,已经被拆分塞进子红包的金额
        int useMoeny = 0;
        for (int i = 0; i < redPackageNumber; i++) {
            if(i == redPackageNumber - 1){
                redPackageNumbers[i] = totalMoney - useMoeny;
            }else {
                int avgMoney = ((totalMoney - useMoeny) / (redPackageNumber - i)) * 2;
                redPackageNumbers[i] = 1 + new Random().nextInt(avgMoney -1);
            }
            useMoeny = useMoeny + redPackageNumbers[i];
        }
        return redPackageNumbers;
    }
}
