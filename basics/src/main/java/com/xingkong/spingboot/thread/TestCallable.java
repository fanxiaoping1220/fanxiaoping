package com.xingkong.spingboot.thread;

import java.util.concurrent.*;

/**
 * * @className: TestCallable
 * * @description: 线程的创建方式
 * * 实现 callable 重写 call
 * * @author: fan xiaoping
 * * @date: 2022/6/3 0003 21:23
 **/
public class TestCallable implements Callable<Boolean> {
    @Override
    public Boolean call() throws Exception {
        System.out.println("当前线程为："+Thread.currentThread().getName());
        return true;
    }

    public static void main(String[] args) {
        //1.创建执行服务 创建3个线程
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //2.创建线程，提交线程，运行call方法
        Future<Boolean> t1 = executorService.submit(new TestCallable());
        Future<Boolean> t2 = executorService.submit(new TestCallable());
        Future<Boolean> t3 = executorService.submit(new TestCallable());
        try {
            //获取结果
            Boolean r1 = t1.get();
            System.out.println("r1="+r1);
            Boolean r2 = t2.get();
            System.out.println("r2="+r2);
            Boolean r3 = t3.get();
            System.out.println("r3="+r3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //3.关闭服务
        executorService.shutdown();
    }
}
