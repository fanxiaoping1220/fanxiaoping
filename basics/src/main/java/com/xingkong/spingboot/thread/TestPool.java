package com.xingkong.spingboot.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * * @className: TestPool
 * * @description: 测试线程池
 * * @author: fan xiaoping
 * * @date: 2022/6/23 0023 17:33
 **/
public class TestPool {

    public static void main(String[] args) {
        //创建固定线程池
        ExecutorService service = Executors.newFixedThreadPool(50);
        //执行
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());
        service.execute(new MyThread());

        //关闭线程池
        service.shutdown();
    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread()+"i="+i);
        }
    }
}
