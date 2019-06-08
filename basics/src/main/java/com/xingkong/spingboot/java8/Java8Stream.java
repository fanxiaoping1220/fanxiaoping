package com.xingkong.spingboot.java8;

import com.xingkong.spingboot.entity.Artist;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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

    }
}
