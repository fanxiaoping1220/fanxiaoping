package com.xingkong.spingboot.java8;

/**
 * @className: Java8LambdaTest
 * @description: Lambda 表达式
 * @author: 范小平
 * @date: 2019-04-18 10:10
 * @version: 1.0.0
 */
public class Java8LambdaTest {

    final static String salutation = "hello";

    public static void main(String[] args) {
        Java8LambdaTest test = new Java8LambdaTest();
        //类型的声明
        MathOperation mathOperation = (int a,int b) -> a + b;
        //不用类型的声明
        MathOperation mathOperation1 = (a,b) -> a - b;
        // 大括号中的返回语句
        MathOperation mathOperation2 = (int a, int b) -> {return a * b;};
        // 没有大括号及返回语句
        MathOperation mathOperation3 = (int a , int b) -> a/b;

        System.out.println("mathOperation: " + test.operate(10,5,mathOperation));
        System.out.println("mathOperation1: " + test.operate(10,5,mathOperation1));
        System.out.println("mathOperation2: " + test.operate(10,5,mathOperation2));
        System.out.println("mathOperation3: " + test.operate(10,5,mathOperation3));

        // 不用括号
        GreetingService greetingService = message -> System.out.println("hello " + message);
        // 用括号
        GreetingService greetingService1 = (message) -> System.out.println("hello " + message);
        greetingService.sayMessage("Runoob");
        greetingService1.sayMessage("Google");

        GreetingService greetingService2 = (message -> System.out.println(salutation + message));
        greetingService2.sayMessage("Runoob");

        int num = 1;
        Converter<Integer,String> converter = (params) -> System.out.println(num+params);
        converter.convert(2);
    }

    interface MathOperation{
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    interface Converter<T1, T2> {
        void convert(int i);
    }

    private int operate(int a ,int b,MathOperation mathOperation){
        return mathOperation.operation(a,b);
    }

}