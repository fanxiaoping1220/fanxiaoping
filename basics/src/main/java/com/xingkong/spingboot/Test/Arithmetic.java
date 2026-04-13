package com.xingkong.spingboot.test;

import com.xingkong.spingboot.commonutil.AccurateCalculation;

/**
 * 算术操作符
 * Created by 46926 on 2018/8/4.
 *
 * @author fanxiaoping
 */
public class Arithmetic {

    public static void main(String[] args) {
        /**
         * 总长度为1500.999米
         * 每分钟为10米
         * 需要多久
         */
        double distance = 1505.999;
        double sum = 10;
        System.out.println(AccurateCalculation.add(distance, sum, 2));//1516.00
        System.out.println(AccurateCalculation.sub(distance, sum, 2));//1496.00
        System.out.println(AccurateCalculation.mul(distance, sum, 2));//15059.99
        System.out.println(AccurateCalculation.div(distance, sum, 2));//150.6
    }
}
