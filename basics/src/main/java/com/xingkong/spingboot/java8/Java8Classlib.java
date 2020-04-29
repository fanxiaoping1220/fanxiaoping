package com.xingkong.spingboot.java8;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/**
 * @ClassName: Java8Classlib
 * @Description: 类库
 * @Auther: fanxiaoping
 * @Date: 2019/6/15 18:20
 * @version: 1.0.0
 */
public class Java8Classlib {

    private static final Logger logger = LoggerFactory.getLogger(Java8Classlib.class);

    public void debug(Supplier<String> message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message.get());
        }
    }

    private interface IntegerBiFunction extends BinaryOperator<Integer> {

    }

    private static void overloadedMethod(BinaryOperator<Integer> lambda) {
        System.out.println("BinaryOperator");
    }

    private static void overloadedMethod(IntegerBiFunction lambda) {
        System.out.println("IntegerBinaryOperator");
    }

    public static void main(String[] args) {
        overloadedMethod((integer, integer2) -> integer + integer2);
    }
}
