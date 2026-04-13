package com.xingkong.spingboot.reflection;

/**
 * * @className: Test4
 * * @description: 测试类加载
 * * @author: fan xiaoping
 * * @date: 2022/7/29 0029 16:25
 **/
public class Test4 {

    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.m);
        /**
         * 1.加载到内存，会产生一个对应的class对象
         * 2.链接，链接结束后m=100
         * 3.初始化
         * <clinit>{
         *     System.out.println("A类静态代码块初始化!");
         *     m = 200;
         *     m = 100;
         * }
         * m = 100
         */
    }
}

class A {
    static {
        System.out.println("A类静态代码块初始化!");
        m = 200;
    }

    static int m = 100;

    public A() {
        System.out.println("A类无参构造初始化!");
    }
}
