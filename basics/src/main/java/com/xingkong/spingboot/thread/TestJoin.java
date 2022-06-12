package com.xingkong.spingboot.thread;

/**
 * * @className: TestJoin
 * * @description: join测试
 * * join合并线程，待此线程执行完毕后，在执行其他线程，其他线程阻塞
 * * 插队执行
 * * @author: fan xiaoping
 * * @date: 2022/6/12 0012 22:26
 **/
public class TestJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 200; i++) {
                System.out.println("线程vip来了i="+i);
            }
        });
        thread.start();
        for (int i = 0; i < 1000; i++) {
            if(i == 200){
                //插队
                thread.join();
            }
            System.out.println("main i="+i);
        }

    }
}
