package com.xingkong.spingboot.version2x.ioc;

/**
 * * @className: Person
 * * @description: 人类接口
 * * @author: fanxiaoping
 * * @date: 2020/10/26 17:42
 **/
public interface Person {

    /**
     * 使用动物的服务
     */
    void service();

    /**
     * 设置动物
     * @param animal
     */
    void setAnimal(Animal animal);
}
