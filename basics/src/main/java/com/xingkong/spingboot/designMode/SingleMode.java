package com.xingkong.spingboot.designMode;

import java.io.Serializable;

/**
 * @author fanxiaoping
 * @className: SingleMode
 * @description: 单例模式  饿汉模式
 * 优点：在类加载时就完成了初始化，所以类加载比较慢，但获取对象的速度快。
 * 缺点：容易造成资源浪费
 * @date 2020-03-16 23:22:00
 */
public class SingleMode implements Serializable {

    private static SingleMode singleMode = new SingleMode();

    private SingleMode() {
    }

    public static SingleMode getInstance(){
        return singleMode;
    }

    public void showMessage(){
        System.out.println("hello singleMode");
    }
}
