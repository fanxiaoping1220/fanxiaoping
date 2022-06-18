package com.xingkong.spingboot.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * * @className: UnsafeList
 * * @description: 模拟线程不安全的集合
 * * @author: fan xiaoping
 * * @date: 2022/6/18 0018 17:55
 **/
public class UnsafeList {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
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
