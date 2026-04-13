package com.xingkong.spingboot.designMode;

/**
 * @author fanxiaoping
 * @className: SingleMode2
 * @description: 单例模式 懒汉模式 双检锁模式
 * 在类加载时不初始化，等到第一次被使用时才初始化。
 * @date 2020-03-17 00:43:16
 */
public class SingleMode2 {

    /**
     * volatile 保证不会进行指令重排
     * 1.分配对象内存空间
     * 2.初始化对象
     * 3.instance指向1的分配空间
     * 在多线程情况下可能会出现指令重排的情况第二部和第三部可能会调换
     * 特性：
     * 保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。（实现可见性）
     * 禁止进行指令重排序。（实现有序性）
     * volatile 只能保证对单次读/写的原子性。i++ 这种操作不能保证原子性。
     */
    private static volatile SingleMode2 singleMode2;

    private SingleMode2() {
    }

    /**
     * 采用加锁的模式
     * 线程安全的
     *
     * @return
     */
    public static SingleMode2 getInstance() {
        if (singleMode2 == null) {
            synchronized (SingleMode2.class) {
                if (singleMode2 == null) {
                    singleMode2 = new SingleMode2();
                }
            }
        }
        return singleMode2;
    }

}
