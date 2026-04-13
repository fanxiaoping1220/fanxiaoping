package com.xingkong.spingboot.Annotation;

import java.lang.annotation.*;

/**
 * * @className: TestAnnotation
 * * @description: 自定义注解
 * * @author: fan xiaoping
 * * @date: 2022/7/26 0026 14:43
 **/
@MyAnnotation
public class TestAnnotation {

    @MyAnnotation
    public void test(){

    }
}

//表示这个注解可以用在哪个地方
@Target(value = {ElementType.TYPE,ElementType.METHOD})
//表示这个注解可以在什么地方有效
//runtime > class > source
@Retention(value = RetentionPolicy.RUNTIME)
//表示这个注解可以生成在javadoc中
@Documented
//表示之类可以继承父类的注解
@Inherited
@interface MyAnnotation{

}
