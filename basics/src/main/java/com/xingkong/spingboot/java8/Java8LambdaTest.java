package com.xingkong.spingboot.java8;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        MathOperation mathOperation = (int a, int b) -> a + b;
        //不用类型的声明
        MathOperation mathOperation1 = (a, b) -> a - b;
        // 大括号中的返回语句
        MathOperation mathOperation2 = (int a, int b) -> {
            return a * b;
        };
        // 没有大括号及返回语句
        MathOperation mathOperation3 = (int a, int b) -> a / b;

        System.out.println("mathOperation: " + test.operate(10, 5, mathOperation));
        System.out.println("mathOperation1: " + test.operate(10, 5, mathOperation1));
        System.out.println("mathOperation2: " + test.operate(10, 5, mathOperation2));
        System.out.println("mathOperation3: " + test.operate(10, 5, mathOperation3));

        // 不用括号
        GreetingService greetingService = message -> System.out.println("hello " + message);
        // 用括号
        GreetingService greetingService1 = (message) -> System.out.println("hello " + message);
        greetingService.sayMessage("Runoob");
        greetingService1.sayMessage("Google");

        GreetingService greetingService2 = (message -> System.out.println(salutation + message));
        greetingService2.sayMessage("Runoob");

        int num = 1;
        Converter<Integer, String> converter = (params) -> System.out.println(num + params);
        converter.convert(2);

        Runnable runnable = () -> System.out.println("Hello World");
        ActionListener actionListener = event -> System.out.println("button clicked");
        Runnable runnable1 = () -> {
            System.out.println("Hello");
            System.out.println("World");
        };
        BinaryOperator<Long> add = (x, y) -> x + y;
        BinaryOperator<Long> addExplicit = (Long x, Long y) -> x + y;
        System.out.println(runnable);
        System.out.println(actionListener);
        System.out.println(runnable1);
        System.out.println(add);
        System.out.println(addExplicit);
        String[] array = {"Hello", "World"};
        System.out.println(Arrays.asList(array));
        JButton button = new JButton();
        button.addActionListener(e -> System.out.println(e.getActionCommand()));
        List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());
        System.out.println(list);
        List<String> list1 = Stream.of("a", "b", "c").map(s -> s.toUpperCase()).collect(Collectors.toList());
        System.out.println(list1);
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }

    interface Converter<T1, T2> {
        void convert(int i);
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }

}