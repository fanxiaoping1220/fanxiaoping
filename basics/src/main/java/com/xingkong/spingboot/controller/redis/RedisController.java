package com.xingkong.spingboot.controller.redis;

import com.xingkong.spingboot.version2x.transactional.User;
import com.xingkong.spingboot.version2x.transactional.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * * @className: RedisController
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/12 10:16
 **/
@RequestMapping(value = "/redis")
@RestController
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/stringAndHash")
    public Map<String,Object> testStringAndHash(){
        redisTemplate.opsForValue().set("key1","value");
        redisTemplate.opsForValue().set("int_key","1");
        stringRedisTemplate.opsForValue().set("int","1");
        //运算加+1
        stringRedisTemplate.opsForValue().increment("int",1);
        String int1 = stringRedisTemplate.opsForValue().get("int");
        //获取底层jedis连接
//        Jedis jedis = (Jedis) stringRedisTemplate.getConnectionFactory().getConnection().getNativeConnection();
//        jedis.decr("int");
        Map<String,String> hash = new HashMap<>();
        hash.put("field1","value1");
        hash.put("field2","value2");
        stringRedisTemplate.opsForHash().putAll("hash",hash);
        //新增一个字段
        stringRedisTemplate.opsForHash().put("hash","filed3","value3");
        BoundHashOperations<String, Object, Object> hash1 = stringRedisTemplate.boundHashOps("hash");
        //删除2个字段
        hash1.delete("filed1","filed2");
        hash1.put("filed4","value4");
        Map<String,Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }

    @GetMapping(value = "/list")
    public Map<String,Object> testList(){
        //顺序从左到右
        stringRedisTemplate.opsForList().leftPushAll("list1","v1","v2","v3","v4","v5");
        //顺序从右到左
        stringRedisTemplate.opsForList().rightPushAll("list2","v2","v4","v6","v8","v10");
        //绑定list2操作
        BoundListOperations<String, String> list2 = stringRedisTemplate.boundListOps("list2");
        //从右边弹出第一个成员
        String s = list2.rightPop();
        //获取指定位置的元素
        String index = list2.index(2);
        //从左边插入
        list2.leftPush("v0");
        //长度
        Long size = list2.size();
        List<String> range = list2.range(0, size - 2);
        Map<String,Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }

    @GetMapping(value = "/set")
    public Map<String,Object> testSet(){
        //不允许重复
        stringRedisTemplate.opsForSet().add("set1","v1","v2","v3","v3","v4","v5");
        stringRedisTemplate.opsForSet().add("set2","v1","v2","v8","v9","v10");
        //绑定set1操作
        BoundSetOperations<String, String> set1 = stringRedisTemplate.boundSetOps("set1");
        //增加2个元素
        set1.add("v6","v7");
        //删除2个元素
        set1.remove("v1","v7");
        //返回所有原元素
        Set<String> members = set1.members();
        //长度
        Long size = set1.size();
        //求交集
        Set<String> inter = set1.intersect("set2");
        //求交集，并且用新集合inter保存
        set1.intersectAndStore("set2","inter");
        //求差集
        Set<String> diff = set1.diff("set2");
        //求差集，并且用新集合diff保存
        set1.diffAndStore("set2","diff");
        //并集
        Set<String> union = set1.union("set2");
        set1.unionAndStore("set2","union");
        Map<String,Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }

    @GetMapping(value = "/zset")
    public Map<String,Object> testZset(){
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            //分数
            double score = i * 1;
            //创建一个TypedTuple对象，存入值和分数
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("value"+i,score);
            typedTupleSet.add(typedTuple);
        }
        //往有序集合插入元素
        stringRedisTemplate.opsForZSet().add("zset1",typedTupleSet);
        //绑定zset1操作
        BoundZSetOperations<String, String> zset1 = stringRedisTemplate.boundZSetOps("zset1");
        //增加一个元素
        zset1.add("value101",101);
        Set<String> range = zset1.range(1, 10);
        //按分数排序获取有序集合
        Set<String> setScore = zset1.rangeByScore(10, 20);
        //删除元素
        zset1.remove("value10","value11");
        //求分数
        Double value50 = zset1.score("value50");
        //按下标区间取
        Set<ZSetOperations.TypedTuple<String>> index = zset1.rangeWithScores(10, 20);
        //按分数区间取
        Set<ZSetOperations.TypedTuple<String>> scoreRange = zset1.rangeByScoreWithScores(30, 40);
        //按从大到小排序
        Set<String> reverseRange = zset1.reverseRange(50, 60);
        Map<String,Object> result = new HashMap<>();
        result.put("success",true);
        return result;
    }

    @GetMapping(value = "/getUser")
    public User getUser(@RequestParam("id") Long id){
        return userService.getById(id);
    }

    @PostMapping(value = "/add")
    public User add(@RequestBody User user){
        return userService.add(user);
    }

    @PostMapping(value = "/update")
    public User update(@RequestBody User user){
        return userService.updateById(user);
    }

    @DeleteMapping(value = "/deleteById")
    public int deleteById(@RequestParam("id") Long id){
        return userService.deleteById(id);
    }
}
