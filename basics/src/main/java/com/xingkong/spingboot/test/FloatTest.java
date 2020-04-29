package com.xingkong.spingboot.test;

import java.math.BigDecimal;

/**
 * @ClassName: FloatTest
 * @Description: 浮点数测试
 * @Auther: fanxiaoping
 * @Date: 2019/8/17 16:27
 * @version: 1.0.0
 */
public class FloatTest {

    public static void main(String[] args) {
        //第一种 判断浮点数是否相等的方式
        float a = 1.0f - 0.9f;//0.10000024
        float b = 0.9f - 0.8f;//0.09999964
        if (a == b) {//false
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        //第二种 判断浮点数是否相等的方式
        Float x = Float.valueOf(a);
        Float y = Float.valueOf(b);
        if (x == y) {//false
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        //第三种 判断浮点数是否相等的方式
        Float m = new Float(a);
        Float n = new Float(b);

        if (m == n) {//false
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        float g = 1.0f - 0.9f;
        float h = 0.9f - 0.8f;

        double diff = 1e-6;
        if (Math.abs(g - h) < diff) {//true
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        BigDecimal q = new BigDecimal(1.0);
        BigDecimal w = new BigDecimal(0.9);
        BigDecimal e = new BigDecimal(0.8);
        BigDecimal r = q.subtract(w);
        BigDecimal t = w.subtract(e);

        if (r.equals(t)) {//true
            System.out.println(true);
        } else {
            System.out.println(false);
        }

        float ff = 0.9f;
        double dd = 0.9;

        System.out.println(ff / 1.0);//.089999999976
        System.out.println(dd / 1.0);//0.9
    }

}
