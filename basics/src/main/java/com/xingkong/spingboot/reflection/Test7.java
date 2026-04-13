package com.xingkong.spingboot.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * * @className: Test7
 * * @description: 获得类的信息
 * * @author: fan xiaoping
 * * @date: 2022/8/3 0003 22:33
 **/
public class Test7 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException {
        Class<?> c1 = Class.forName("com.xingkong.spingboot.reflection.User");
        //获得类的名字
        System.out.println(c1.getName());//获得包名 + 类名
        System.out.println(c1.getSimpleName());//获得类名
        //获得类的属性
        Field[] fields = c1.getFields();//只能找到public属性
        for (Field field : fields) {
            System.out.println(field);
        }
        fields = c1.getDeclaredFields();//找到全部的属性
        for (Field field : fields) {
            System.out.println(field);
        }
        //获得指定属性的值
        Field name = c1.getDeclaredField("name");
        System.out.println(name);
        //获得类的方法
        System.out.println("====================");
        Method[] methods = c1.getMethods();//获得本类及父类的全部public方法
        for (Method method : methods) {
            System.out.println("正常的："+method);
        }
        System.out.println("===================");
        methods = c1.getDeclaredMethods();//获得本类的所有方法
        for (Method method : methods) {
            System.out.println("getDeclaredMethods: "+method);
        }
        //获得指定的方法
        Method getAge = c1.getMethod("getAge", null);
        Method setAge = c1.getMethod("setAge", Integer.class);
        System.out.println(getAge);
        System.out.println(setAge);
        //获得指定的构造器
        Constructor<?>[] constructors = c1.getConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println(constructor);
        }
        Constructor<?>[] declaredConstructors = c1.getDeclaredConstructors();
        for (Constructor<?> declaredConstructor : declaredConstructors) {
            System.out.println(declaredConstructor);
        }
        //获得指定的构造器
        Constructor<?> constructor = c1.getConstructor(String.class, Integer.class, Integer.class);
        Constructor<?> constructor1 = c1.getConstructor(null);
        System.out.println("有参："+constructor);
        System.out.println("无参："+constructor1);
        Constructor<?> declaredConstructor = c1.getDeclaredConstructor(String.class,Integer.class,Integer.class);
        System.out.println(declaredConstructor);
        //获得父类
        Class<?> superclass = c1.getSuperclass();
        System.out.println("super："+superclass);
        //获得所有的接口
        Class<?>[] interfaces = c1.getInterfaces();
        for (Class<?> anInterface : interfaces) {
            System.out.println("接口："+anInterface);
        }
        //获得所有的注解
        Annotation[] annotations = c1.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("注解："+annotation);
        }
    }
}
