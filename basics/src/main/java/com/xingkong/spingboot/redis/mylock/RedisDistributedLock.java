package com.xingkong.spingboot.redis.mylock;

import cn.hutool.core.util.IdUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * * @className: RedisDistributedLock
 * * @description:我们自研的redis分布式锁，实现了lock接口
 * * @author: fan xiaoping
 * * @date: 2024/2/20 0020 10:26
 **/
//@Component 引入DistributedLockFactory工厂模式，从工厂获得即可
public class RedisDistributedLock implements Lock {

    private StringRedisTemplate stringRedisTemplate;

    //KEYS[1]
    private String lockName;

    //ARGV[1]
    private String uuidValue;

    //ARGV[2]
    private long expireTime;

    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate,String lockName) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
        this.expireTime = 50L;
        this.uuidValue = IdUtil.simpleUUID() +":"+ Thread.currentThread().getId();
    }

    public RedisDistributedLock(StringRedisTemplate stringRedisTemplate, String lockName, String uuid) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.lockName = lockName;
        this.uuidValue = uuid+":"+ Thread.currentThread().getId();
        this.expireTime = 50L;
    }

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        try {
            tryLock(-1L,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if(time == -1L){
            String script = "if redis.call('exists',KEYS[1]) == 0  or redis.call('hexists',KEYS[1],ARGV[1]) == 1 then" +
                            "   redis.call('hincrby',KEYS[1],ARGV[1],1)" +
                            "   redis.call('expire',KEYS[1],ARGV[2])" +
                            "   return 1 " +
                            "else" +
                            "  return 0 " +
                            "end";
            System.out.println("加锁:"+"lockname:"+lockName+"\t"+"uuidVale:"+uuidValue);
            Boolean flag = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(lockName), uuidValue, String.valueOf(expireTime));
            while (!flag){
                //暂停60毫秒
                TimeUnit.MILLISECONDS.sleep(60);
            }
            //新建一个后台程序扫描,来检测key目前的ttl，是否到我们规定的1/2 1/3来实现续期
            renewExpire();
            return true;
        }
        return false;
    }

    private void renewExpire() {
        String script = "if redis.call('HEXISTS',KEYS[1],ARGV[1]) == 1 then " +
                            "return redis.call('expire',KEYS[1],ARGV[2]) " +
                        "else " +
                            "return  0 " +
                        "end";
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("redis key:"+lockName+" ttl:"+stringRedisTemplate.getExpire(lockName));
                if (stringRedisTemplate.execute(new DefaultRedisScript<>(script,Boolean.class), Arrays.asList(lockName),uuidValue,String.valueOf(expireTime))) {
                    System.out.println("redis key:"+lockName+" ttl:"+stringRedisTemplate.getExpire(lockName));
                    renewExpire();
                }
            }
        },(this.expireTime * 1000)/3);
    }

    @Override
    public void unlock() {
        String script = "if redis.call('hexists',KEYS[1],ARGV[1]) == 0 then" +
                        "   return nil " +
                        "elseif redis.call('hincrby',KEYS[1],ARGV[1],-1) == 0 then" +
                        "   return redis.call('del',KEYS[1]) " +
                        "else " +
                        "  return 0 " +
                        "end";
        System.out.println("解锁:"+"lockname:"+lockName+"\t"+"uuidVale:"+uuidValue);
        //nil=false 1=true 0=false
        Long flag = stringRedisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(lockName), uuidValue);
        if(null == flag){
            throw  new RuntimeException("this lock doesn't exists!");
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
