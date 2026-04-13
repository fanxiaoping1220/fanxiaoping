package com.xingkong.spingboot.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @className: Java8SortTest
 * @description: java7 与 java8 的排序
 * @author: 范小平
 * @date: 2019-04-10 16:37
 * @version: 1.0.0
 */
public class Java8SortTest {

    public static void main(String[] args) {
        List<String> names1 = new ArrayList<>();
        names1.add("Google ");
        names1.add("Runoob ");
        names1.add("Taobao ");
        names1.add("Baidu ");
        names1.add("Sina ");

        List<String> names2 = new ArrayList<>();
        names2.add("Google ");
        names2.add("Runoob ");
        names2.add("Taobao ");
        names2.add("Baidu ");
        names2.add("Sina ");

        Java8SortTest test = new Java8SortTest();
        System.out.println("使用 Java 7 语法: ");
        test.sortUsingJava7(names1);
        System.out.println(names1);

        System.out.println("使用 Java 8 语法: ");
        test.sortUsingJava8(names2);
        System.out.println(names2);
    }

    /**
     * Java 7 的排序
     *
     * @param list
     */
    private void sortUsingJava7(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * Java 8 的排序
     *
     * @param list
     */
    private void sortUsingJava8(List<String> list) {
        Collections.sort(list, (s1, s2) -> s1.compareTo(s2));
    }

}