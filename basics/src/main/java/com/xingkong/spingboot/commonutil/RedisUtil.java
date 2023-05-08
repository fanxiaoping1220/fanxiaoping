package com.xingkong.spingboot.commonutil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxiaoping
 * @className: RedisUtil
 * @description:
 * @date 2019-12-12 14:03:37
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <T> T get(String key) {
        if (key == null) {
            return null;
        }
        Object object = redisTemplate.opsForValue().get(key);
        return (T) object;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存删除
     *
     * @param key 键
     * @return true成功 false失败
     */
    public boolean remove(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 模糊删除
     *
     * @param key 键
     * @return true成功 false失败
     */
    public boolean fuzzyRemovePre(String key) {
        try {
            Set<String> keys = redisTemplate.keys("*" + key);
            if (keys == null || keys.isEmpty()) {
                return false;
            }
            redisTemplate.delete(keys);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public <T> T hget(String key, String item) {
        Object object = redisTemplate.opsForHash().get(key, item);
        return (T) object;
    }

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public <T> T hgets(String key, List<Object> item) {
        Object object = redisTemplate.opsForHash().multiGet(key, item);
        return (T) object;
    }


    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建,自增
     *
     * @param value 自增值
     * @param key   键
     * @param item  项
     * @return true 成功 false失败
     */
    public boolean hsetIncr(String key, String item, long value) {
        try {
            redisTemplate.opsForHash().increment(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 删除hash的key 对应value的值
     *
     * @param key 键
     * @return true 成功 false失败
     */
    public boolean hmremove(String key) {
        try {
            redisTemplate.opsForHash().delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash的某一项的值
     *
     * @param key  键
     * @param item 项
     * @return true 成功 false失败
     */
    public boolean hremove(String key, String... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * set
     * 将 key中的value移动到destKey里面
     * @param key
     * @param destKey
     * @param value
     * @return
     */
    public Boolean setMove(String key,String destKey,Object value){
        try {
            return redisTemplate.opsForSet().move(key, value, destKey);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * set
     * 从key随机弹出N个元素,并删除
     * @param key
     * @param count
     * @return
     */
    public Object setPop(String key,long count){
        try {
            Object pop = redisTemplate.opsForSet().pop(key,count);
            return pop;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * set
     * 从key展示N个值,值不删除
     * @param key
     * @param number
     * @return
     */
    public List<?> setRandomMembers(String key,long number){
        try {
            List list = redisTemplate.opsForSet().randomMembers(key, number);
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * set
     * key,otherKey求差集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<?> setDifference(String key,String otherKey){
        try {
            Set difference = redisTemplate.opsForSet().difference(key, otherKey);
            return difference;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * set
     * key,otherKey求并集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<?> setUnion(String key,String otherKey){
        try {
            Set union = redisTemplate.opsForSet().union(key, otherKey);
            return union;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * set
     * key,otherKey求交集
     * @param key
     * @param otherKey
     * @return
     */
    public Set<?> setIntersect(String key,String otherKey){
        try {
            Set intersect = redisTemplate.opsForSet().intersect(key, otherKey);
            return intersect;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//  --------------------------------------------------------------------------------zSet------------------------------------------------------------------------------------------------------

    /**
     * zSet
     * 添加元素
     * @param key key
     * @param value 值
     * @param score 分数
     * @return
     */
    public boolean zSetAdd(String key,Object value,double score){
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * zSet
     * 从小到大安装score进行排序 正序
     * start 开始位置 end结束位置 0 -1表示全部
     * @param key
     * @param start 开始坐标
     * @param end 结束坐标
     * @return
     */
    public Set<?> zSetRange(String key,long start,long end){
        try {
            return redisTemplate.opsForZSet().range(key,start,end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 获取指定分数范围的
     * 正序 从min到max之间的
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Set<?> zSetRangeByScore(String key,double min,double max){
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 正序
     * 获取指定分数范围的
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param offset 开始位置
     * @param count 显示个数
     * @return
     */
    public Set<?> zSetRangeByScore(String key,double min,double max,long offset,long count){
        try {
            return redisTemplate.opsForZSet().rangeByScore(key,min,max,offset,count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 正序
     * 获取指定分数范围的 + 显示分数
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param offset 开始位置
     * @param count 显示个数
     * @return
     */
    public Set<?> zSetRangeByScoreWithScores(String key,double min,double max,long offset,long count){
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max,offset,count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 正序
     * 获取指定分数范围的 + 显示分数
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Set<?> zSetRangeByScoreWithScores(String key,double min,double max){
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 倒序 从大到小
     * 从大到小按照分数进行排序
     * start 0 end -1 表示全部
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public Set<?> zSetReverseRange(String key,long start,long end){
        try {
            return redisTemplate.opsForZSet().reverseRange(key,start,end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 倒序
     * 获取指定分数范围的
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Set<?> zSetReverseRangeByScore(String key,double min,double max){
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 倒序
     * 获取指定分数范围的
     * offset 0 count 2 ==> limit 0 2
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param offset 开始位置
     * @param count 显示个数
     * @return
     */
    public Set<?> zSetReverseRangeByScore(String key,double min,double max,long offset,long count){
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key,min,max,offset,count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 倒序
     * 获取指定分数范围的 + 显示分数
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @param offset 开始位置
     * @param count 显示个数
     * @return
     */
    public Set<?> zSetReverseRangeByScoreWithScores(String key,double min,double max,long offset,long count){
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,min,max,offset,count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 倒序
     * 获取指定分数范围内 + 显示分数
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Set<?> zSetReverseRangeByScoreWithScores(String key,double min,double max){
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 获取元素的分数
     * @param key key
     * @param value 元素
     * @return
     */
    public Double zSetGetValueScore(String key,Object value){
        try {
            return redisTemplate.opsForZSet().score(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *zSet
     * 获取key中的元素数量
     * size
     * @param key key
     * @return
     */
    public Long zSetGetSize(String key){
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 删除
     * 根据value删除
     * @param key
     * @param value
     * @return
     */
    public Long zSetRemove(String key,Object... value){
        try {
            return redisTemplate.opsForZSet().remove(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 删除
     * 根据位置范围删除
     * @param key key
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    public Long zSetRemoveRange(String key,long start,long end){
        try {
            return redisTemplate.opsForZSet().removeRange(key,start,end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 删除
     * 根据分数范围删除
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return
     */
    public Long zSetRemoveRangeByScore(String key,double min,double max){
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 增加某个元素的分数
     * @param key key
     * @param value value
     * @param score + 分数
     * @return
     */
    public Double zSetIncrementScore(String key,Object value,double score){
        try {
            return redisTemplate.opsForZSet().incrementScore(key,value,score);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 获取指定分数范围内的元素个数
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zSetCount(String key,double min,double max){
        try {
            return redisTemplate.opsForZSet().count(key,min,max);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 正序 获取value的下标值
     * @param key
     * @param value
     * @return
     */
    public Long zSetRank(String key,Object value){
        try {
            return redisTemplate.opsForZSet().rank(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * zSet
     * 倒序 获取value的下标值
     * @param key
     * @param value
     * @return
     */
    public Long zSetReverseRank(String key,Object value){
        try {
            return redisTemplate.opsForZSet().reverseRank(key,value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//  --------------------------------------------------------------------bitmap----------------------------------------------------------------------------------------------------------

    /**
     * bitmap
     * set
     * @param key key
     * @param offset 偏移位
     * @param value 值1或0
     * @return
     */
    public Boolean bitSet(String key,long offset,Boolean value){
        try {
            return redisTemplate.opsForValue().setBit(key,offset,value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * bitmap
     * get
     * @param key key
     * @param offset 偏移量
     * @return
     */
    public Boolean bitGet(String key,long offset){
        try {
            return redisTemplate.opsForValue().getBit(key,offset);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * bitFiled 位域
     * @param key
     * @param subCommands
     * @return
     */
    public List<Long> bitFiled(String key, BitFieldSubCommands subCommands){
        try {
            return redisTemplate.opsForValue().bitField(key,subCommands);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加锁(redis分布式锁)
     *
     * @param key
     * @param value 当前线程操作时的 System.currentTimeMillis() + 2000，2000是超时时间，这个地方不需要去设置redis的expire，
     *              也不需要超时后手动去删除该key，因为会存在并发的线程都会去删除，造成上一个锁失效，结果都获得锁去执行，并发操作失败了就。
     * @return
     */
    public boolean lock(String key, String value) {
        //如果key值不存在，则返回 true，且设置 value
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        //获取key的值，判断是是否超时
        String curVal = redisTemplate.opsForValue().get(key).toString();
        if (StringUtils.isNotEmpty(curVal) && Long.parseLong(curVal) < System.currentTimeMillis()) {
            //获得之前的key值，同时设置当前的传入的value。这个地方可能几个线程同时过来，但是redis本身天然是单线程的，所以getAndSet方法还是会安全执行，
            //首先执行的线程，此时curVal当然和oldVal值相等，因为就是同一个值，之后该线程set了自己的value，后面的线程就取不到锁了
            String oldVal = redisTemplate.opsForValue().getAndSet(key, value).toString();
            if (StringUtils.isNotEmpty(oldVal) && oldVal.equals(curVal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String curVal = redisTemplate.opsForValue().get(key).toString();
            if (StringUtils.isNotEmpty(curVal) && curVal.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
