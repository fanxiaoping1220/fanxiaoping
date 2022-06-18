package com.xingkong.spingboot.thread;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * * @className: TestJUC
 * * @description:  测试JUC安全类型的集合
 * * @author: fan xiaoping
 * * @date: 2022/6/18 0018 22:01
 **/
public class TestJUC {

    public static void main(String[] args) {
        //CopyOnWriteArrayList 线程安全的集合
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
            }).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size());
    }
}
