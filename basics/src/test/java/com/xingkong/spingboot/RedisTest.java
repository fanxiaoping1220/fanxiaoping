package com.xingkong.spingboot;

import com.xingkong.spingboot.commonutil.RedisUtil;
import io.lettuce.core.cluster.SlotHash;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * * @className: RedisTest
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/5/6 0006 15:42
 **/
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * zSet
     * add
     */
    @Test
    public void zSetAdd(){
        redisUtil.zSetAdd("zset","v1",65.1);
        redisUtil.zSetAdd("zset","v2",75.1);
        redisUtil.zSetAdd("zset","v3",85.1);
        redisUtil.zSetAdd("zset","v4",95.1);
        redisUtil.zSetAdd("zset","v5",105.1);
        redisUtil.zSetAdd("zset","v6",55.1);
    }

    /**
     * zSet
     * 从小到大
     * range
     */
    @Test
    public void zSetRange(){
        Set<String> zSet = (Set<String>) redisUtil.zSetRange("zset", 0, -1);
        Set<?> zSet1 = redisUtil.zSetRangeByScore("zset", 60, 90);
        Set<?> zSet2 = redisUtil.zSetRangeByScore("zset", 70, 100, 0, 2);
        Set<?> zSet3 = redisUtil.zSetRangeByScoreWithScores("zset", 60, 100);
        Set<?> zSet4 = redisUtil.zSetRangeByScoreWithScores("zset", 60, 100, 0, 3);
        System.out.println(zSet4);
        System.out.println(zSet3);
        System.out.println(zSet);
        System.out.println(zSet2);
        System.out.println(zSet1);
    }

    /**
     * zSet
     * 从大到小
     * reverseRange
     */
    @Test
    public void zSetReverseRange(){
        Set<?> zSet = redisUtil.zSetReverseRange("zset", 0, -1);
        Set<?> zSet1 = redisUtil.zSetReverseRangeByScore("zset", 60, 90);
        Set<?> zSet2 = redisUtil.zSetReverseRangeByScoreWithScores("zset", 60, 100, 0, 2);
        Set<?> zSet3 = redisUtil.zSetReverseRangeByScoreWithScores("zset", 60, 90);
        Set<?> zSet4 = redisUtil.zSetReverseRangeByScoreWithScores("zset", 60, 90, 0, 2);
        System.out.println(zSet);
        System.out.println(zSet1);
        System.out.println(zSet2);
        System.out.println(zSet3);
        System.out.println(zSet4);
    }

    /**
     * zSet
     * score
     * 获取元素的分数
     */
    @Test
    public void zSetValueScore(){
        Double score = redisUtil.zSetGetValueScore("zset", "v5");
        System.out.println(score);
    }

    /**
     * zSet
     * size
     */
    @Test
    public void zSetSize(){
        Long zset = redisUtil.zSetGetSize("zset");
        System.out.println(zset);
    }

    /**
     * zSet
     * remove
     */
    @Test
    public void zSetRemove(){
        Long status = redisUtil.zSetRemove("zset", "v1");
        Long status2 = redisUtil.zSetRemove("zset", "v7");
        Long status3 = redisUtil.zSetRemoveRange("zset", 0, 2);
        Set<?> zset = redisUtil.zSetRange("zset", 0, -1);
        Long status4 = redisUtil.zSetRemoveRangeByScore("zset", 70, 80);
        Set<?> zset1 = redisUtil.zSetRange("zset", 0, -1);
        System.out.println(status);
        System.out.println(status2);
        System.out.println(status3);
        System.out.println(status4);
        System.out.println(zset);
        System.out.println(zset1);
    }

    /**
     * zSet
     * increment
     */
    @Test
    public void zSetIncrBy(){
        Double score = redisUtil.zSetIncrementScore("zset", "v4", 50);
        System.out.println(score);
    }

    /**
     * zSet
     * count
     */
    @Test
    public void zSetCount(){
        Long count = redisUtil.zSetCount("zset", 90, 110);
        System.out.println(count);
    }

    /**
     * zSet
     * rank
     */
    @Test
    public void zSetRank(){
        Set<?> zset = redisUtil.zSetRange("zset", 0, -1);
        Long index = redisUtil.zSetRank("zset", "v5");
        System.out.println(index);
        System.out.println(zset);
    }

    /**
     * zSet
     * reverseRank
     */
    @Test
    public void zSetReverseRank(){
        Long index = redisUtil.zSetReverseRank("zset", "v5");
        Set<?> zset = redisUtil.zSetReverseRange("zset", 0, -1);
        System.out.println(index);
        System.out.println(zset);
    }

    /**
     * set
     * add
     */
    @Test
    public void setAdd(){
        long l = redisUtil.sSet("set", "v1", "v2", "v3", 11, 22, 33);
        long l1 = redisUtil.sSet("set2", "v1", "v4", "v5", 11, 22, 88);
        Set<Object> set = redisUtil.sGet("set");
        Set<Object> set1 = redisUtil.sGet("set2");
        System.out.println(l);
        System.out.println(l1);
        System.out.println(set);
        System.out.println(set1);
    }

    /**
     * set
     * 差集
     * difference
     */
    @Test
    public void setDiff(){
        //v2,v3,88
        Set<?> set = redisUtil.setDifference("set", "set2");
        System.out.println(set);
    }

    /**
     * set
     * 并集
     * union
     */
    @Test
    public void setUnion(){
        //v1 v2 v3 v4 v5 11 22 33 88
        Set<?> setUnion = redisUtil.setUnion("set", "set2");
        System.out.println(setUnion);
    }

    /**
     * set
     * 交集
     * intersect
     */
    @Test
    public void setInter(){
        //v1 11 22
        Set<?> intersect = redisUtil.setIntersect("set2", "set");
        System.out.println(intersect);
    }

    /**
     * bitmap
     * set
     */
    @Test
    public void bitset(){
        redisUtil.bitSet("k1:202305",0,true);
        redisUtil.bitSet("k1:202305",1,true);
        redisUtil.bitSet("k1:202305",2,false);
        redisUtil.bitSet("k1:202305",3,true);
        redisUtil.bitSet("k1:202305",4,true);
        redisUtil.bitSet("k1:202305",5,true);
        redisUtil.bitSet("k1:202305",6,false);
        redisUtil.bitSet("k1:202305",7,false);
    }

    /**
     * bitmap
     * get
     */
    @Test
    public void bitGet(){
        Boolean one = redisUtil.bitGet("k1:202305", 0);
        Boolean two = redisUtil.bitGet("k1:202305", 1);
        Boolean three = redisUtil.bitGet("k1:202305", 2);
        Boolean five = redisUtil.bitGet("k1:202305", 3);
        Boolean four = redisUtil.bitGet("k1:202305", 4);
        Boolean six = redisUtil.bitGet("k1:202305", 5);
        Boolean seven = redisUtil.bitGet("k1:202305", 6);
        Boolean eight = redisUtil.bitGet("k1:202305", 7);
        System.out.println(one);
        System.out.println(two);
        System.out.println(three);
        System.out.println(five);
        System.out.println(four);
        System.out.println(six);
        System.out.println(seven);
        System.out.println(eight);
        int dayOfMonth = LocalDate.now().getDayOfMonth();
        List<Long> result = redisUtil.bitFiled("k1:202305", BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0));
        System.out.println(result);
        Long number = result.get(0);
        int count = 0;
        while (true){
            if((number & 1) == 0){
                break;
            }else {
                count++;
            }
            number >>>= 1;
        }
        System.out.println(count);
    }

    /**
     * hash
     * set
     */
    @Test
    public void hSet(){
        redisUtil.hset("hash","v1","xx");
        redisUtil.hset("hash","v2","yy");
        redisUtil.hset("hash","v3","11");
        redisUtil.hset("hash","v4","22");
    }

    /**
     * hash
     * getFields
     */
    @Test
    public void hashGetFieldS(){
        Set<Object> fields = redisUtil.hGetFields("hash");
        System.out.println(fields);
    }

    /**
     * hash
     * getValues
     */
    @Test
    public void HashGetValues(){
        List<Object> values = redisUtil.hGetValues("hash");
        System.out.println(values);
    }

    /**
     * hyperLogLog
     * add
     */
    @Test
    public void hyperLogLogAdd(){
        Long hyperLogLog = redisUtil.hyAdd("hyperLogLog", 1, 3, 5, 7, 9, 8, 0);
        Long hyperLogLog2 = redisUtil.hyAdd("hyperLogLog2", 11, 22, 33, 4, 4, 4, 55, 55, 6, 7, 8, 10);
        System.out.println(hyperLogLog);
        System.out.println(hyperLogLog2);
    }

    /**
     * hyperLogLog
     * count
     */
    @Test
    public void hyperLogLogCount(){
        Long hyperLogLog = redisUtil.hyCount("hyperLogLog");
        Long hyperLogLog2 = redisUtil.hyCount("hyperLogLog2");
        Long hyperLogLog3 = redisUtil.hyCount("hyperLogLog2","hyperLogLog");
        System.out.println(hyperLogLog);
        System.out.println(hyperLogLog2);
        System.out.println(hyperLogLog3);
    }

    /**
     * hyperLogLog
     * union
     */
    @Test
    public void hyperLogLogUnion(){
        Long union = redisUtil.hyUnion("resultKey", "hyperLogLog", "hyperLogLog2");
        Long resultKey = redisUtil.hyCount("resultKey");
        System.out.println(union);
        System.out.println(resultKey);
    }

    /**
     * hyperLogLog
     * delete
     */
    @Test
    public void hyperLogLogDelete(){
        redisUtil.hyDelete("resultKey");
        Long resultKey = redisUtil.hyCount("resultKey");
        System.out.println(resultKey);
    }

    /**
     * geo
     * add
     */
    @Test
    public void geoAdd(){
        redisUtil.geoAdd("city",new Point(119.083293,27.624457),"巧姐烟酒");
        redisUtil.geoAdd("city",new Point(119.083791,27.624509),"中洁管道");
        redisUtil.geoAdd("city",new Point(119.084285,27.624553),"顾家家居");
        redisUtil.geoAdd("city",new Point(119.083922,27.625045),"庆元农商银行");
        redisUtil.geoAdd("city",new Point(119.083692,27.625265),"金球影城");
        redisUtil.geoAdd("city",new Point(119.083616,27.625433),"海纳百川大浴场");
        redisUtil.geoAdd("city",new Point(119.083203,27.625681),"k歌之王");
        redisUtil.geoAdd("city",new Point(119.083203,27.625681),"金丝玉玛瓷砖");
        redisUtil.geoAdd("city",new Point(119.084721,27.625249),"和家园");
        redisUtil.geoAdd("city",new Point(119.081986,27.624769),"温州银行");
    }

    /**
     * geo
     * position
     */
    @Test
    public void geoPosition(){
        List<Point> points = redisUtil.geoPosition("city", "巧姐烟酒", "中洁管道", "温州银行");
        points.forEach(point -> {
            System.out.println(point);
        });
    }

    /**
     * geo
     * hash
     */
    @Test
    public void geoHash(){
        List<String> strings = redisUtil.geoHash("city", "和家园", "k歌之王", "庆元农商银行");
        strings.forEach(s -> {
            System.out.println(s);
        });
    }

    /**
     * geo
     * distance
     * 2个地点之间距离
     */
    @Test
    public void geoDistance(){
        //m
        Distance distance = redisUtil.geoDistance("city", "温州银行", "庆元农商银行", RedisGeoCommands.DistanceUnit.METERS);
        //km
        Distance distance2 = redisUtil.geoDistance("city", "温州银行", "金丝玉玛瓷砖", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println(distance);
        System.out.println(distance2);
    }

    /**
     * geo
     * radius
     * 范围内的经纬度
     */
    @Test
    public void geoRadius(){
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs();
        geoRadiusCommandArgs.limit(2);
        geoRadiusCommandArgs.sortDescending();
        GeoResults<RedisGeoCommands.GeoLocation> geoResults = redisUtil.geoRadius("city", "庆元农商银行", new Distance(50, RedisGeoCommands.DistanceUnit.METERS));
        GeoResults<RedisGeoCommands.GeoLocation> geoResult2 = redisUtil.geoRadius("city", new Point(119.083922,27.625045),new Distance(100, RedisGeoCommands.DistanceUnit.METERS));
        System.out.println(geoResults);
        System.out.println(geoResult2);
        System.out.println(geoResults.getAverageDistance());
        geoResults.getContent().forEach(geoLocationGeoResult -> {
            System.out.println(geoLocationGeoResult);
            System.out.println(geoLocationGeoResult.getContent().getName());
            List<Point> city = redisUtil.geoPosition("city", geoLocationGeoResult.getContent().getName());
            city.forEach(point -> {
                System.out.println(point);
            });
        });
    }

    /**
     * geo
     * remove
     */
    @Test
    public void geoRemove(){
        Long aLong = redisUtil.geoRemove("city", "温州银行", "巧姐烟酒");
        System.out.println(aLong);
    }

    /**
     * redis集群cluster
     * 哈希槽的计算
     */
    @Test
    public void hashSlot(){
        System.out.println(SlotHash.getSlot("A"));
        System.out.println(SlotHash.getSlot("B"));
        System.out.println(SlotHash.getSlot("b"));
        System.out.println(SlotHash.getSlot("a"));
    }

}
