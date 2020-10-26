package com.xingkong.spingboot.version2x.ioc;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * * @className: Cat
 * * @description: 猫 动物的实现类
 * * @author: fanxiaoping
 * * @date: 2020/10/26 17:52
 **/
//优先权
@Primary
@Service
public class Cat implements Animal {

    @Override
    public void use() {
        System.out.println("猫，【"+Cat.class.getSimpleName()+"】是用来抓老鼠的");
    }
}
