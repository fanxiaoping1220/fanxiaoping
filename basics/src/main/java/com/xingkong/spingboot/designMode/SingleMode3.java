package com.xingkong.spingboot.designMode;

/**
 * @author fanxiaoping
 * @className: SingleMode3
 * @description:单例模式 静态内部类(跟懒汉模式效果一样)
 * 因为类的静态属性只会在第一次加载类的时候初始化，也就保证了SingletonInstance中的对象只会被实例化一次，并且这个过程也是线程安全的。
 * @date 2020-03-17 01:07:02
 */
public class SingleMode3 {

    /**
     * 为了解决反射的方式创建的对象照成的攻击
     */
    private SingleMode3(){
        if(SingleModeInstance.SINGLE_MODE != null){
            throw new RuntimeException();
        }
    }

    private static class SingleModeInstance{
        private static final SingleMode3 SINGLE_MODE = new SingleMode3();
    }

    public static SingleMode3 getInstance(){
        return SingleModeInstance.SINGLE_MODE;
    }
}
