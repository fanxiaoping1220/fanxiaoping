package com.xingkong.spingboot.reflection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * * @className: Test
 * * @description: 测试反射
 * * @author: fan xiaoping
 * * @date: 2022/7/26 0026 16:38
 **/
public class Test {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> c1 = Class.forName("com.xingkong.spingboot.reflection.User");
        System.out.println(c1);
        //一个类在内存中只有一个class对象
        //一个类被加载后，类的整个结果都会封装在class对象中
        Class<?> c2 = Class.forName("com.xingkong.spingboot.reflection.User");
        Class<?> c3 = Class.forName("com.xingkong.spingboot.reflection.User");
        Class<?> c4 = Class.forName("com.xingkong.spingboot.reflection.User");
        System.out.println(c2.hashCode());
        System.out.println(c3.hashCode());
        System.out.println(c4.hashCode());
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class User{

    private String name;

    private Integer age;

    private Integer id;

    private void test(){

    }
}

