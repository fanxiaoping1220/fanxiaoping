package com.xingkong.spingboot.java8;

import com.xingkong.spingboot.entity.Artist;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

/**
 * @ClassName: Java8Stream
 * @Description: 常用的流操作
 * @Auther: fanxiaoping
 * @Date: 2019/6/2 20:48
 * @version: 1.0.0
 */
public class Java8Stream {

    public static void main(String[] args) {
        //1.collect(toList())
        List<String> list = Stream.of("a","b","c").collect(toList());
        System.out.println(list);
        //2.map
        List<String> list1 = Stream.of("a","b","c").map(s -> s.toUpperCase()).collect(toList());
        System.out.println(list1);
        //3.filter
        List<String> collect = Stream.of("a", "1abc", "abc1").filter(value -> isDigit(value.charAt(0))).collect(toList());
        System.out.println(collect);
        //4.flatMap方法可用Stream替换值，然后将多个Stream连接成一个Stream
        List<Integer> collect1 = Stream.of(asList(1, 2), asList(3, 4)).flatMap(numbers -> numbers.stream()).collect(toList());
        System.out.println(collect1);
        //5.max和min
        List<Integer> integers = asList(524, 378, 451);
        Integer integer1 = integers.stream().min(Comparator.comparing(integer -> integer)).get();
        System.out.println(integer1);
        Integer integer2 = integers.stream().max(Comparator.comparing(integer -> integer)).get();
        System.out.println(integer2);
        //排序
        Collections.sort(integers,(o1, o2) -> o1.compareTo(o2));
        System.out.println(integers);
        integers = integers.stream().sorted((o1, o2) -> o1.compareTo(o2)).collect(toList());
        System.out.println(integers);
        //6.reduce 求和
        Integer reduce = Stream.of(1, 5, 8, 96).reduce(0, (acc, element) -> acc + element);
        System.out.println(reduce);
        //7.forEach
        //7.1
        List<Integer> numberList = asList(12,16,147,128,36,25,96,45612,96,77,147);
        Set<Integer> track = new HashSet<>();
        for(Integer i :numberList){
            if(i > 88){
                track.add(i);
            }
        }
        System.out.println(track);
        Set<Integer> track1 = new HashSet<>();
        Set<Integer> track2 = numberList.stream().filter(integer -> integer > 88).collect(toSet());
        numberList.stream().forEach(integer -> {
            if(integer > 88){
                track1.add(integer);
            }
        });
        System.out.println(track1);
        System.out.println(track2);

        //test
        //1.求和
        Double reduce1 = Stream.of(15.26, 158.96, 253.69, 25369.25).reduce(0.00, (number, number2) -> number + number2);
        System.out.println(reduce1);
        //2.
        List<Artist> artistList = Stream.of(new Artist("xxx", "china", LocalDateTime.now()),
                new Artist("fff", "hhhh", LocalDateTime.now()),
                new Artist("yyyy", "bbbbb", LocalDateTime.now()))
                .map(artist -> new Artist(artist.getName(), artist.getNationality()))
                .collect(toList());
        System.out.println(artistList);
        //3.
        Integer sum = Stream.of(new Artist(15),
                new Artist(18), new Artist(20), new Artist(11))
                .map(artist -> artist.getNumber())
                .reduce(0, (integer, number) -> integer + number);
        System.out.println(sum);

        //将数据分组 partitioningBy
        List<Artist> artists = asList(new Artist("fxp","LMS",1,LocalDateTime.now(),true),new Artist("lazy","LMS",12,LocalDateTime.now(),true),new Artist("zyt","LPL",12,LocalDateTime.now(),false),new Artist("performance","LCK",12,LocalDateTime.now(),false));
        Map<Boolean, List<Artist>> partitioningMap = artists.stream().collect(partitioningBy(artist -> artist.getSolo()));
        System.out.println(partitioningMap);
        //将数据分组 groupBy
        Map<Integer, List<Artist>> groupMap = artists.stream().collect(groupingBy(artist -> artist.getNumber()));
        System.out.println(groupMap);
        //字符串连接 joining
        String names = artists.stream().map(artist -> artist.getName()).collect(joining(",", "[", "]"));
        System.out.println(names);
        //组合收集器 方案一
        Map<String, List<Artist>> collectMap = artists.stream().collect(groupingBy(artist -> artist.getNationality()));
        Map<String,Integer> groupCountMap = new HashMap<>();
        for (Map.Entry<String,List<Artist>> map : collectMap.entrySet()){
            groupCountMap.put(map.getKey(),map.getValue().size());
        }
        System.out.println(groupCountMap);
        //方案一 优化版
        Map<String, Long> groupOptimizeCountMap = artists.stream().collect(groupingBy(artist -> artist.getNationality(), counting()));
        System.out.println(groupOptimizeCountMap);
        //方案二
        Map<String,List<String>> groupCountTwoMap = new HashMap<>();
        for (Map.Entry<String,List<Artist>> map : collectMap.entrySet()){
            groupCountTwoMap.put(map.getKey(),map.getValue().stream().map(artist -> artist.getName()).collect(toList()));
        }
        System.out.println(groupCountTwoMap);
        //方案二 优化版
        Map<String, List<String>> groupTwoOptimizeMap = artists.stream().collect(groupingBy(artist -> artist.getNationality(), mapping(artist -> artist.getName(), toList())));
        System.out.println(groupTwoOptimizeMap);
        //map 遍历
        groupTwoOptimizeMap.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
        });
        //平均值计算
        Double avgCount = Stream.of(15.23, 15.369, 258.1, 12354, 25864.456).collect(averagingDouble(value -> value.doubleValue()));
        System.out.println(avgCount);
        //求和值计算
        DoubleSummaryStatistics sumCount = Stream.of(15.23, 15.369, 258.1, 12354, 25864.456).collect(summarizingDouble(value -> value.doubleValue()));
        System.out.println("平均值:"+sumCount.getAverage()+" 求和统计:"+sumCount.getSum()+" max:"+sumCount.getMax()+"min:"+sumCount.getMin());
        //初始化数组
        double[] doubles = new double[10];
        Arrays.parallelSetAll(doubles,i -> i);
        System.out.println(doubles);
        doubles[1] = 19.23;
        //排序
        Arrays.parallelSort(doubles);
        System.out.println(doubles.toString());
        //简单计算滑到平均值
        Arrays.parallelPrefix(doubles,Double :: sum);
        System.out.println(doubles);
        int start = 3;
        double[] doubles1 = IntStream.range(start, doubles.length).mapToDouble(i -> {
            double prefix = i == start ? 0 : doubles[i - 2];
            return (doubles[i] - prefix) / 2;
        }).toArray();
        System.out.println(doubles1);
    }
}