package com.xingkong.spingboot.designMode;

/**
 * @author fanxiaoping
 * @className: SingleMode2
 * @description: 单例模式 懒汉模式
 * 在类加载时不初始化，等到第一次被使用时才初始化。
 * @date 2020-03-17 00:43:16
 */
public class SingleMode2 {

    private static SingleMode2 singleMode2;

    private SingleMode2() {
    }

    /**
     * 采用加锁的模式
     * 线程安全的
     * @return
     */
    public static SingleMode2 getInstance(){
        if(singleMode2 == null){
            synchronized (SingleMode2.class){
                if (singleMode2 == null){
                    singleMode2 = new SingleMode2();
                }
            }
        }
        return singleMode2;
    }

}
