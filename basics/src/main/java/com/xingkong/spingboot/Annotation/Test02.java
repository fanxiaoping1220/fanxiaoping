package com.xingkong.spingboot.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * * @className: Test02
 * * @description: 自定义注解
 * * @author: fan xiaoping
 * * @date: 2022/7/26 0026 15:02
 **/
public class Test02 {

    //注解可以显示赋值，如果没有默认值，我们就必须给注解赋值
    @MyAnnotation02(name = "星辰")
    public void test(){

    }

    @MyAnnotation3("星辰")
    public void test2(){

    }
}

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation02{

    //注解的参数：参数类型 + 参数名()
    String name() default "";

    int age() default 0;

    //如果默认值为-1，则代表不存在
    int id() default -1;

    String[] schools() default {"清华大学","北京大学"};

}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@interface MyAnnotation3{
    String value();
}
