package com.xingkong.spingboot.reflection;

/**
 * * @className: Test5
 * * @description: 测试类什么时候会初始化
 * * @author: fan xiaoping
 * * @date: 2022/7/29 0029 17:00
 **/
public class Test5 {
    static {
        System.out.println("main类被加载");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        //1.主动引用
        Son son = new Son();
        //2.反射也会产生主动引用
        Class.forName("com.xingkong.spingboot.reflection.Son");
        //不会产生类的引用方法
        //1.子类调用父类的静态变量
        System.out.println(Son.b);
        //2.数组的方式
        Son[] arrry = new Son[10];
        //3.子类的final
        System.out.println(Son.M);
    }
}

class Father{
    static int b = 2;
    static {
        System.out.println("父类被加载");
    }
}

class Son extends Father{
    static {
        System.out.println("子类被加载");
        m = 300;
    }
    static int  m = 100;
    static final int  M = 1000;
}