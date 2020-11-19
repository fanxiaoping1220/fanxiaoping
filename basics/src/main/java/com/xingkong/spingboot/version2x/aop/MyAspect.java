package com.xingkong.spingboot.version2x.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * * @className: MyAspect
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/10/29 14:57
 **/
@Aspect
@Component
public class MyAspect {

    /**
     * 之前
     * 一
     */
    @Before("execution( * com.xingkong.spingboot.version2x.aop.UsersService.printUser(..))")
    public void before(){
        System.out.println("before..............");
    }

    /**
     * 之后
     * 二
     */
    @After("execution( * com.xingkong.spingboot.version2x.aop.UsersService.printUser(..))")
    public void after(){
        System.out.println("after..............");
    }

    /**
     * 运行正常
     * 三
     */
    @AfterReturning("execution( * com.xingkong.spingboot.version2x.aop.UsersService.printUser(..))")
    public void afterReturning(){
        System.out.println("afterReturning...........");
    }

    /**
     * 运行异常
     * 三
     */
    @AfterThrowing("execution( * com.xingkong.spingboot.version2x.aop.UsersService.printUser(..))")
    public void afterThrowing(){
        System.out.println("afterThrowing............");
    }
}
