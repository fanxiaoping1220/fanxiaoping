package com.xingkong.spingboot.designMode;

/**
 * @author fanxiaoping
 * @className: SingleMode3
 * @description:单例模式  枚举模式
 * @date 2020-03-17 01:07:02
 */
public enum SingleMode4 {

    INSTANCE{
        @Override
        protected void doSomething(){
            System.out.println("doSomething");
        }
    };

    protected abstract void doSomething();
}
