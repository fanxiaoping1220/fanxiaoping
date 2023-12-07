package com.xingkong.spingboot.service.impl;

import com.xingkong.spingboot.commonutil.RedisUtil;
import com.xingkong.spingboot.entity.Product;
import com.xingkong.spingboot.service.JHSTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * * @className: JHSTaskServiceImpl
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/12/6 0006 16:59
 **/
@Slf4j
@Service
public class JHSTaskServiceImpl implements JHSTaskService {

    public static final String JHS_KEY = "jhs";

    public static final String JHS_KEY_A = "jhs:a";

    public static final String JHS_KEY_B = "jhs:b";

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 偷个懒不加mysql了,模拟从数据库去20件特价商品,用于加载到聚划算的页面中
     * @return
     */
    @Override
    public List<Product> getProductsFromMysql() {
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int id = random.nextInt(10000);
            Product product = new Product((long) id,"product"+i,i,"detail");
            list.add(product);
        }
        return list;
    }

    /**
     * 模拟初始化数据
     */
    @PostConstruct
    public void initJHS(){
        log.info("启动定时器天猫聚划算功能模拟开始。。。。。。。。。。。。。。。。。。。");
        //1.用线程模拟定时器任务,后台任务定时将mysql里面的参加活动的商品刷新到redis里
        new Thread(() -> {
            while (true){
                //2.模拟mysql查出数据，用于加载到redis并给聚划算页面显示
                List<Product> list = this.getProductsFromMysql();
                //3.采用redis list数据结构的lpush命令来实现存储
                redisUtil.del(JHS_KEY);
                //4.加入最新的数据给redis参加活动
                redisUtil.lSet(JHS_KEY,list.toArray());
                //5.暂停1分钟,间隔一分钟执行一次,模拟聚划算一天执行的参加活动的品牌
                try {
                    TimeUnit.MINUTES.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },"t1").start();
    }

    /**
     * 模拟初始化数据
     * AB双缓存
     */
    @PostConstruct
    public void initJHSAB(){
        //1.用线程模拟定时任务，后台任务定时将mysql里面的参加活动的商品刷新到redis里
        log.info("启动AB定时器计划任务天猫聚划算功能模拟。。。。。。。。{}", LocalDateTime.now());
        new Thread(() -> {
           while (true){
               //2.模拟从msyql查出数据,用于加载到redis并给聚划算页面显示
               List<Product> list = this.getProductsFromMysql();
               //3.先更新缓存B且让B缓存过期时间超过A,如果A突然失效了还有B兜底,防止缓存击穿
               redisUtil.del(JHS_KEY_B);
               redisUtil.lSet(JHS_KEY_B,86410,list.toArray());
               //4.在更新A缓存
               redisUtil.del(JHS_KEY_A);
               redisUtil.lSet(JHS_KEY_A,86400,list.toArray());
               //5.暂停1分钟线程,间隔一分钟执行一次,模拟聚划算一天执行的参加活动的品牌
               try {
                   TimeUnit.MINUTES.sleep(1);
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
        },"t1").start();
    }
}
