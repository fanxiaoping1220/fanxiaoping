package com.xingkong.spingboot.thread;

/**
 * * @className: TestYeld
 * * @description: 线程礼让
 * * 让cpu重新调度，礼让不一定成功，看cpu心情
 * * @author: fan xiaoping
 * * @date: 2022/6/12 0012 22:14
 **/
public class TestYeld {

    public static void main(String[] args) {
        MyYeld myYeld = new MyYeld();
        new Thread(myYeld,"a").start();
        new Thread(myYeld,"b").start();
    }
}

class MyYeld implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程开始执行");
        Thread.yield();
        System.out.println(Thread.currentThread().getName()+"线程停止执行");
    }
}
