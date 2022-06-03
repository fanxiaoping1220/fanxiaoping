package com.xingkong.spingboot.thread;

/**
 * * @className: TestLambda
 * * @description: lambda表达式
 * * @author: fan xiaoping
 * * @date: 2022/6/3 0003 22:28
 **/
public class TestLambda {
    public static void main(String[] args) {
        ILike like = () -> {
            System.out.println("lambda表达式实现方式");
        };
        like.lambda();
    }
}

interface ILike{
    void lambda();
}
