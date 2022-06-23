package com.xingkong.spingboot.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * * @className: CreateThreadMethod
 * * @description: 创建线程的方式
 * * @author: fan xiaoping
 * * @date: 2022/6/23 0023 22:15
 **/
public class CreateThreadMethod {

    public static void main(String[] args) {
        new MyThread1().start();
        new Thread(new MyThread2()).start();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread3());
        new Thread(futureTask).start();
        try {
            Integer result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyThread1 extends Thread {
    @Override
    public void run() {
        System.out.println("MyThread1");
    }
}

class MyThread2 implements Runnable {

    @Override
    public void run() {
        System.out.println("MyThread2");
    }
}

class MyThread3 implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("MyThread3");
        return 100;
    }
}
