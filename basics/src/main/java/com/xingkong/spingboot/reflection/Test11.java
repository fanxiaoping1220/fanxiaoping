package com.xingkong.spingboot.reflection;

import lombok.Data;

import java.lang.annotation.*;
import java.lang.reflect.Field;

/**
 * * @className: Test11
 * * @description:练习反射操作注解
 * * @author: fan xiaoping
 * * @date: 2022/9/21 0021 9:42
 **/
public class Test11 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException {
        Class<?> c1 = Class.forName("com.xingkong.spingboot.reflection.Student2");
        //通过反射获得注解
        Annotation[] annotations = c1.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println(annotation);
        }
        //获得注解value的值
        TableUser annotation = c1.getAnnotation(TableUser.class);
        String value = annotation.value();
        System.out.println(value);
        Field[] fields = c1.getDeclaredFields();
        for (Field field : fields) {
            FieldUser annotation1 = field.getAnnotation(FieldUser.class);
            String columnName = annotation1.columnName();
            System.out.println(columnName);
            String type = annotation1.type();
            System.out.println(type);
            int length = annotation1.length();
            System.out.println(length);
        }
        Field id = c1.getDeclaredField("id");
        FieldUser annotation1 = id.getAnnotation(FieldUser.class);
        System.out.println(annotation1.columnName());
        System.out.println(annotation1.type());
        System.out.println(annotation1.length());
    }
}

@TableUser(value = "db-user")
@Data
class Student2{
    @FieldUser(columnName = "db-id",type = "int",length = 10)
    private int id;

    @FieldUser(columnName = "db-age",type = "int",length = 2)
    private int age;

    @FieldUser(columnName = "db-name",type = "varchar",length = 100)
    private String name;
}

//类名的注解
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface TableUser{
    String value();
}

//属性的注解
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface FieldUser{
    String columnName();
    String type();
    int length();
}
