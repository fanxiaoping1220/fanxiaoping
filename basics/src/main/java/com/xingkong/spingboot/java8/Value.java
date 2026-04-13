package com.xingkong.spingboot.java8;

/**
 * * @className: Value
 * * @description: Java 8编译器在类型推断方面有很大的提升，在很多场景下编译器可以推导出某个参数的数据类型，从而使得代码更为简洁。例子代码如下：
 * * @author: fanxiaoping
 * * @date: 2020-07-20  16:03
 **/
public class Value<T> {

    public static <T> T defaultValue(){
        return null;
    }

    public T getOrDefault(T value, T defaultValue){
        return (value != null) ? value : defaultValue;
    }

    public static void main(String[] args) {
        final Value<String> value = new Value<>();
        System.out.println(value.getOrDefault("22", defaultValue()));
    }
}
