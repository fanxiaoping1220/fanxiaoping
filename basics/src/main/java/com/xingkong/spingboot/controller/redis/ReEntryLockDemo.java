package com.xingkong.spingboot.controller.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * * @className: ReEntryLockDemo
 * * @description: 可重入锁demo
 * * 在一个synchronize修饰的方式或代码块的内部调用本来的其他synchronize修饰的方法或代码块时,是永远可以得到锁的
 * * @author: fan xiaoping
 * * @date: 2023/12/28 0028 14:10
 **/
public class ReEntryLockDemo {

    public static final Object obj = new Object();

    public static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        new ReEntryLockDemo().demo();
        new ReEntryLockDemo().demo2();
        new ReEntryLockDemo().demo3();
    }

    private void demo3() {
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"外层调用lock");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"中层调用lock");
                }finally {
                    lock.unlock();
                }
            }finally {
                lock.unlock();
            }
        },"t2").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"外层调用lock");
            }finally {
                lock.unlock();
            }
        },"t3").start();
    }

    private void demo2() {
        m1();
    }

    private synchronized void m1() {
        System.out.println(Thread.currentThread().getName()+"外层调用");
        m2();
    }

    private synchronized void m2() {
        System.out.println(Thread.currentThread().getName()+"中层调用");
        m3();
    }

    private synchronized void m3() {
        System.out.println(Thread.currentThread().getName()+"内层调用");
    }

    public void demo() {
        new Thread(() -> {
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+"外层调用");
                synchronized (obj) {
                    System.out.println(Thread.currentThread().getName()+"中层调用");
                    synchronized (obj) {
                        System.out.println(Thread.currentThread().getName()+"内层调用");
                    }
                }
            }
        },"t1").start();
    }
}
