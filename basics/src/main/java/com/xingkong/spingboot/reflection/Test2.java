package com.xingkong.spingboot.reflection;

import lombok.Data;

/**
 * * @className: Test2
 * * @description: 测试class类的创建方式有哪些
 * * @author: fan xiaoping
 * * @date: 2022/7/26 0026 17:04
 **/
public class Test2 {

    public static void main(String[] args) throws ClassNotFoundException {
        Person person = new Student();
        System.out.println("这个人是:"+person.name);
        //方式一 通过对象获得
        Class<? extends Person> c1 = person.getClass();
        System.out.println(c1.hashCode());
        //方式二 通过forName获得
        Class<?> c2 = Class.forName("com.xingkong.spingboot.reflection.Student");
        System.out.println(c2.hashCode());
        //方式三 通过.Class对象获得
        Class<Student> c3 = Student.class;
        System.out.println(c3.hashCode());
        //方式四 基本内置类型的包装类都有一个Type属性
        Class<Integer> c4 = Integer.TYPE;
        System.out.println(c4.hashCode());
        System.out.println(c4);
        //获得父类的类型
        Class<?> c5 = c2.getSuperclass();
        System.out.println(c5);
    }
}

@Data
class Person{
    public String name;
}

class Student extends Person{
    public Student() {
        this.name = "学生";
    }
}

class Teacher extends Person{
    public Teacher() {
        this.name = "老师";
    }
}