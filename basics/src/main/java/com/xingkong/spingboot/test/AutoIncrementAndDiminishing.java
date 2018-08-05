package com.xingkong.spingboot.test;

/**
 * @ClassName: AutoIncrementAndDiminishing
 * @Description: 自动递增和递减
 * @Auther: fanxiaoping
 * @Date: 2018/8/5 23:23
 * @version: 1.0.0
 */
public class AutoIncrementAndDiminishing {

    public static void main(String[] args) {

        int i = 0;
        System.out.println(i);
        /** 后缀递增 0*/
        System.out.println("i++ ="+i++);
        /**前缀递增 2*/
        System.out.println("++i ="+(++i));
        /**后缀递减 2*/
        System.out.println("i-- ="+i--);
        /**前缀递减 0*/
        System.out.println("--i ="+(--i));
    }
}
