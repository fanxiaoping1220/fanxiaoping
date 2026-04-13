package com.xingkong.spingboot.thread;

/**
 * * @className: TestPriority
 * * @description: 测试线程优先级
 * * @author: fan xiaoping
 * * @date: 2022/6/16 0016 21:56
 **/
public class TestPriority {
    public static void main(String[] args) {
        //打印主线程名称和优先级
        System.out.println(Thread.currentThread().getName()+"--->"+Thread.currentThread().getPriority());
        MyPriority myPriority = new MyPriority();
        Thread thread = new Thread(myPriority);
        Thread thread2 = new Thread(myPriority);
        Thread thread3 = new Thread(myPriority);
        Thread thread4 = new Thread(myPriority);
        Thread thread5 = new Thread(myPriority);
        Thread thread6 = new Thread(myPriority);

        //先设置优先级在启动
        thread.start();
        thread2.setPriority(Thread.MIN_PRIORITY);
        thread2.start();
        thread3.setPriority(Thread.NORM_PRIORITY);
        thread3.start();
        thread4.setPriority(Thread.MAX_PRIORITY);
        thread4.start();
        //小于最小值抛出异常
//        thread5.setPriority(-1);
//        thread5.start();
        //大于最大值抛出异常
//        thread6.setPriority(11);
//        thread6.start();
    }
}

class MyPriority implements Runnable{

    @Override
    public void run() {
        //打印线程名称,线程优先级
        System.out.println(Thread.currentThread().getName()+"---->"+Thread.currentThread().getPriority());
    }
}
