package com.xingkong.spingboot.thread;

/**
 * * @className: TestThread
 * * @description: 线程的创建方式
 * * 继承 thread 重写run()
 * * @author: fan xiaoping
 * * @date: 2022/6/3 0003 15:30
 **/
public class TestThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("i="+i);
        }
    }

    public static void main(String[] args) {
        new TestThread().start();
        for (int j = 0; j < 100; j++) {
            System.out.println("j="+j);
        }
    }
}
