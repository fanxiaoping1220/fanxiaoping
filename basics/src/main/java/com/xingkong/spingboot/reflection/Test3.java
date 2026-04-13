package com.xingkong.spingboot.reflection;

import java.lang.annotation.ElementType;

/**
 * * @className: Test3
 * * @description: 所有类型的class
 * * @author: fan xiaoping
 * * @date: 2022/7/27 0027 9:38
 **/
public class Test3 {

    public static void main(String[] args) {
        //类
        Class<Object> c1 = Object.class;
        //接口
        Class<Comparable> c2 = Comparable.class;
        //一维数组
        Class<String[]> c3 = String[].class;
        //二维数组
        Class<int[][]> c4 = int[][].class;
        //注解
        Class<Override> c5 = Override.class;
        //基本数据类型
        Class<Integer> c6 = Integer.class;
        //枚举
        Class<ElementType> c7 = ElementType.class;
        //void
        Class<Void> c8 = void.class;
        //class
        Class<Class> c9 = Class.class;
        System.out.println(c1);
        System.out.println(c2);
        System.out.println(c3);
        System.out.println(c4);
        System.out.println(c5);
        System.out.println(c6);
        System.out.println(c7);
        System.out.println(c8);
        System.out.println(c9);

        //只要元素与维度一样，就是同一个class
        int[] a = new int[100];
        int[] b = new int[10];
        System.out.println(a.getClass().hashCode());
        System.out.println(b.getClass().hashCode());
    }
}
