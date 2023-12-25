package com.xingkong.spingboot.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.xingkong.spingboot.commonutil.CheckUtils;
import com.xingkong.spingboot.commonutil.RedisUtil;
import com.xingkong.spingboot.entity.UserRedis;
import com.xingkong.spingboot.mapper.UserRedisMapper;
import com.xingkong.spingboot.redis.filter.BloomFilterInit;
import com.xingkong.spingboot.service.UserRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //1.定义一个常量
    public static final int _1W = 10000;
    //2.定义我们guava布隆过滤器，初始容量
    public static final int SIZE = 100 * _1W;
    //3.误判率,它越小误判的个数也就越少(思考:是否可以是无限小？？没有误判率不是更好)
    public static final double FPP = 0.03;
    //4.创建guava布隆过滤器
    public static final BloomFilter<Integer> BLOOM_FILTER = BloomFilter.create(Funnels.integerFunnel(),SIZE,FPP);
    //黑名单，查看过的不推荐
    public static final BloomFilter<Integer> USER_BLOOM_FILTER = BloomFilter.create(Funnels.integerFunnel(),_1W,FPP);
    //初始化视频
    public static final List<Integer> VIDEO_LIST = new ArrayList<>();
    //锁
    private Lock lock = new ReentrantLock();


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

    @Override
    public void guavaBloomFilter() {
        //1.先让bloomFilter加入100w白名单数据
        for (int i = 1; i <= SIZE; i++) {
            BLOOM_FILTER.put(i);
        }
        //2.故意取10w个不在合法范围内的数据,来进行误判率的演示
        ArrayList<Object> list = new ArrayList<>(10 * _1W);
        //3.验证
        for (int i = SIZE+1; i < SIZE + (10 * _1W); i++) {
            if(BLOOM_FILTER.mightContain(i)){
                log.info("被误判:{}",i);
                list.add(i);
            }
        }
        log.info("误判总数量:{}",list.size());
    }

    @Override
    public Integer getVideo() {
        Random random = new Random();
        Boolean flag = false;
        //2.获取视频
        int index = random.nextInt(10);
        int video = VIDEO_LIST.get(index);
        //3.验证
        while (!flag){
            //已推荐
            if(USER_BLOOM_FILTER.mightContain(video)){
                //重新推荐
                log.info("{}视频已推荐过",video);
                index = random.nextInt(10);
                video = VIDEO_LIST.get(index);
            }else {
                //未推荐
                flag = true;
            }
        }
        USER_BLOOM_FILTER.put(video);
        log.info("推荐视频为:{}",video);
        return  video;
    }

    /**
     * 2.0版
     * 单机版加锁配合nginx和Jmeter压测后，不满足高并发分布式锁的性能要求，出现超卖
     * @return
     */
    @Override
    public String sale() {
        String retMessage = "";
        lock.lock();
        try {
            String result = stringRedisTemplate.opsForValue().get("inventory001");
            Integer inventoryNumber = result == null ? 0 : Integer.parseInt(result);
            if(inventoryNumber > 0){
                stringRedisTemplate.opsForValue().set("inventory001",String.valueOf(--inventoryNumber));
                retMessage = "成功卖出一个商品,库存剩余:"+inventoryNumber;
                System.out.println(retMessage);
            }else {
                retMessage = "商品卖完了";
                System.out.println(retMessage);
            }
        }finally{
            lock.unlock();
        }
        return retMessage;
    }

    /**
     * 3.1版
     * 分布式锁 采用redis锁
     * 通过递归重试的方式，保证抢锁成功
     * 递归重试,容易导致stackOverFlowError,所以不太推荐;另外不太推荐; 另外,高并发唤醒后推荐用while判断而不是if
     * @return
     */
    @Override
    public String sale2() {
        String retMessage = "";
        String key = "zzyyRedisLock";
        String uuidValue = IdUtil.simpleUUID() + Thread.currentThread().getId();
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, uuidValue);
        //flag=false,抢不到的线程要继续重试
        if(!flag){
            try {
                //暂停20毫秒，进行递归重试
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sale2();
        }else {
            //抢锁成功的请求线程,进行正常的业务逻辑,扣减库存,释放锁
            try {
                String result = stringRedisTemplate.opsForValue().get("inventory001");
                Integer inventoryNumber = result == null ? 0 : Integer.parseInt(result);
                if(inventoryNumber > 0){
                    stringRedisTemplate.opsForValue().set("inventory001",String.valueOf(--inventoryNumber));
                    retMessage = "成功卖出一个商品,库存剩余:"+inventoryNumber;
                    System.out.println(retMessage);
                }else {
                    retMessage = "商品卖完了";
                    System.out.println(retMessage);
                }
            }finally{
                stringRedisTemplate.delete(key);
            }
        }
        return retMessage;
    }

    /**
     * 3.2版
     * 3.1的改进版 容易导致stackOverFlowError,所以不太推荐;
     * 用自旋替代递归方法,用while来替代if
     * @return
     */
    @Override
    public String sale3() {
        String retMessage = "";
        String key = "zzyyRedisLock";
        String uuidValue = IdUtil.simpleUUID() + Thread.currentThread().getId();
        //不用递归了,高并发下容易出错,我们用自旋替代递归方法重试调用;也不用if了,用while来替代
        while (!stringRedisTemplate.opsForValue().setIfAbsent(key, uuidValue)){
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //抢锁成功的请求线程,进行正常的业务逻辑,扣减库存,释放锁
        try {
            String result = stringRedisTemplate.opsForValue().get("inventory001");
            Integer inventoryNumber = result == null ? 0 : Integer.parseInt(result);
            if(inventoryNumber > 0){
                stringRedisTemplate.opsForValue().set("inventory001",String.valueOf(--inventoryNumber));
                retMessage = "成功卖出一个商品,库存剩余:"+inventoryNumber;
                System.out.println(retMessage);
            }else {
                retMessage = "商品卖完了";
                System.out.println(retMessage);
            }
        }finally{
            stringRedisTemplate.delete(key);
        }
        return retMessage;
    }

    /**
     * 1.初始化视频
     */
    @PostConstruct
    public void initVideo(){
        VIDEO_LIST.add(1);
        VIDEO_LIST.add(2);
        VIDEO_LIST.add(5);
        VIDEO_LIST.add(6);
        VIDEO_LIST.add(3);
        VIDEO_LIST.add(4);
        VIDEO_LIST.add(9);
        VIDEO_LIST.add(10);
        VIDEO_LIST.add(7);
        VIDEO_LIST.add(8);
    }
}
