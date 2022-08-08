package com.xingkong.spingboot.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * * @className: Test8
 * * @description: 动态的创建对象，通过反射
 * * @author: fan xiaoping
 * * @date: 2022/8/8 0008 23:09
 **/
public class Test8 {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {
        //获得class对象
        Class<?> c1 = Class.forName("com.xingkong.spingboot.reflection.User");

        //构造一个对象
        //本质上是调用了类的无参构造器
        User user = (User) c1.newInstance();
        System.out.println(user);

        //通过构造器创建对象
        Constructor<?> constructor = c1.getDeclaredConstructor(String.class, Integer.class, Integer.class);
        User user2 = (User) constructor.newInstance("星辰", 18, 1234);
        System.out.println(user2);

        //通过反射调用普通方法
        User user3 = (User) c1.newInstance();
        //通过反射获得一个方法
        Method setName = c1.getDeclaredMethod("setName", String.class);
        Method getName = c1.getDeclaredMethod("getName");
        //invoke 激活的意思
        //(对象,方法的值)
        setName.invoke(user3,"星空");
        System.out.println(user3.getName());
        System.out.println(user3);
        System.out.println(getName.invoke(user3));

        //通过反射操作属性
        User user4 = (User) c1.newInstance();
        Field name = c1.getDeclaredField("name");
        //不能直接操作私有属性private,需要关闭程序的安全检测,属性或者方法的setAccessible(true)
        name.setAccessible(true);
        name.set(user4,"星辰");
        System.out.println(user4.getName());
    }
}
