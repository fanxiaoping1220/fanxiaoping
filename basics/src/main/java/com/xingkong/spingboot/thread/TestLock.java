package com.xingkong.spingboot.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * * @className: TestLock
 * * @description: 测试Lock锁
 * * @author: fan xiaoping
 * * @date: 2022/6/18 0018 22:54
 **/
public class TestLock {
    public static void main(String[] args) {
        TestLock2 testLock2 = new TestLock2();
        new Thread(testLock2).start();
        new Thread(testLock2).start();
        new Thread(testLock2).start();
    }
}

class TestLock2 implements Runnable{

    int ticketsNum = 10;

    /**
     * 定义lock锁
     */
    private ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true){
            try{
                //加锁
                lock.lock();
                if(ticketsNum > 0){
                    System.out.println(Thread.currentThread().getName()+"拿到了"+ticketsNum--);
                    Thread.sleep(1000);
                }else {
                    break;
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                //解锁
                lock.unlock();
            }
        }
    }
}
