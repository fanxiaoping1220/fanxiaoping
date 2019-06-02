package com.xingkong.spingboot.java8;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

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


    }
}
