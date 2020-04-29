package com.xingkong.spingboot.commonutil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.UUID;

import static java.util.Arrays.asList;

/**
 * @author fanxiaoping
 * @className: RedisPoolUtil redis分布式锁工具类
 * 详细 https://blog.csdn.net/yb223731/article/details/90349502
 * 分布式锁需要满足的4个条件：
 * 1.互斥锁 在任意时刻只有一个客户端能持有锁
 * 2.不会发生死锁 即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁
 * 3.具有容错性 只要大部分的redis节点正常运行，客户端就可以加锁和解锁
 * 4.解铃还需系铃人 加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了
 * @description:
 * @date 2020-03-21 14:33:30
 */
@Component
public class RedisPoolUtil {

    @Autowired
    private static Jedis jedis = new Jedis();

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final String RELEASE_SUCCESS = "1L";

    /**
     * 尝试获取分布式锁
     * jedis.set(String key, String value, String nxxx, String expx, int time)
     * key: 锁
     * value: 第四个条件 解铃还需系铃人 通过value赋值的requestId，我们就知道这把锁是哪个请求加的，在解锁的时候就可以有依据。 requestId可以使用UUID.randomUUID().toString()方法生成。
     * nxxx:设置NX的意思，SET IF NOT EXIST,即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
     * expx: 设置为PX 意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
     * time: 代码key的过期时间
     * 总的来说：1.当前没有锁（key不存在），把那么久进行加锁操作，并对锁设置个有效期，同时value表示加锁的客户端。
     * 2.已有锁存在，不做任何操作。
     *
     * @param lockKey    锁
     * @param requestId  请求标识
     * @param expireTime 过期时间
     * @return 是否加锁成功
     */
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }

        return false;
    }

    /**
     * 释放分布式锁
     *
     * @param lockKey   锁
     * @param requestId 请求标识
     * @return 是否释放锁成功
     */
    public boolean releaseDistributedLock(String lockKey, String requestId) {

        /** 是否锁脚本 */
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        Object result = jedis.eval(script, asList(lockKey), asList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 获取key 对于的值value,不存在这返回null
     *
     * @param key
     * @return
     */
    public String get(String key) {
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * setkey 若key-value不存在则存入缓存，并且返回1，存在则返回0
     *
     * @param key
     * @param value
     * @return
     */
    public Long setnx(String key, String value) {
        try {
            return jedis.setnx(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * getset
     * 先获取key对应的值value，不存在则返回null，存在则替换value的值
     *
     * @param key
     * @param value
     * @return
     */
    public String getSet(String key, String value) {
        try {
            return jedis.getSet(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 设置key-value的有效期为seconds秒。
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {
        try {
            return jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 删除指定的key
     *
     * @param key
     * @return
     */
    public Long del(String key) {
        try {
            return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

}
