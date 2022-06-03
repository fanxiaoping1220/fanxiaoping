package com.xingkong.spingboot.thread;

/**
 * * @className: TestTwoThread
 * * @description: 线程的创建方式
 * * 实现 Runnable 重写run()
 * * @author: fan xiaoping
 * * @date: 2022/6/3 0003 16:30
 **/
public class TestTwoThread implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("i="+i);
        }
    }

    public static void main(String[] args) {
        new Thread(new TestTwoThread()).start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("i="+i);
            }
        }).start();
        for (int j = 0; j < 200; j++) {
            System.out.println("j="+j);
        }
    }
}
