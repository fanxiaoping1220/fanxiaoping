package com.xingkong.spingboot.java8;

import java.util.function.Supplier;

/**
 * * @className: DefaulableFactory
 * * @description: 下面的代码片段整合了默认方法和静态方法的使用场景：
 * * @author: fanxiaoping
 * * @date: 2020-07-20  15:48
 **/
public interface DefaulableFactory {

    static Defaulable create(Supplier<Defaulable> supplier){
        return supplier.get();
    }

    public static void main(String[] args) {
        Defaulable defaulable = DefaulableFactory.create(Defaulable.DefaultableImpl :: new );
        //Default implementation
        System.out.println(defaulable.notRequired());

        Defaulable overridable = DefaulableFactory.create(Defaulable.OverridableImpl::new);
        //OverridableImpl implementation
        System.out.println(overridable.notRequired());

    }

}
