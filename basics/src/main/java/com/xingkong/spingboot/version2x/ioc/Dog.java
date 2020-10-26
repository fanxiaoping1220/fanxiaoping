package com.xingkong.spingboot.version2x.ioc;

import org.springframework.stereotype.Service;

/**
 * * @className: Dog
 * * @description: 狗 动物的实现类
 * * @author: fanxiaoping
 * * @date: 2020/10/26 17:49
 **/
@Service
public class Dog implements Animal {

    @Override
    public void use() {
        System.out.println("狗["+Dog.class.getSimpleName()+"]是用来看门的");
    }
}
