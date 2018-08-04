package com.xingkong.spingboot.commonUtil;

import java.math.BigDecimal;

/**
 * 精确计算工具类
 * Created by 46926 on 2018/8/4.
 */
public class AccurateCalculation {

    /**
     * 提供精确的加法运算
     * @param value1 被加数
     * @param value2 加数
     * @param scale 表示需要精确小数点后几位
     * @return
     */
    public static double add(double value1,double value2,int scale){
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.add(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * @param value1 被减数
     * @param value2 减数
     * @param scale 表示需要精确小数点后几位
     * @return
     */
    public static double sub(double value1,double value2,int scale){
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * @param value1 被乘数
     * @param value2 乘数
     * @param scale 表示需要精确小数点后几位
     * @return
     */
    public static double mul(double value1,double value2,int scale){
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.multiply(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的除法运算
     * @param value1 被除数
     * @param value2 除数
     * @param scale 表示需要精确小数点后几位
     * @return
     */
    public static double div(double value1,double value2,int scale){
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.divide(b2).setScale(scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        /**
         * BigDecimal.ROUND_UP
         * 舍入远离零的舍入模式
         * 不管后面一位是多少始终+1
         */
        BigDecimal b1 = new BigDecimal(10.1504);
        System.out.println(b1.setScale(2,BigDecimal.ROUND_UP));//10.16
        /**
         * BigDecimal.ROUND_DOWN
         *接近零的舍入模式
         * 直接去掉保留几位后面的小数，从不进行+1
         */
        System.out.println(b1.setScale(2,BigDecimal.ROUND_DOWN));//10.15
        /**
         * BigDecimal.ROUND_CEILING
         * 接近正无穷大的舍入模式
         * 如果 BigDecimal 为正,则舍入行为与 ROUND_UP 相同;
         　　如果为负,则舍入行为与 ROUND_DOWN 相同。
         　　注意,此舍入模式始终不会减少计算值。
         */
        BigDecimal b2 = new BigDecimal(10.1504);
        BigDecimal b3 = new BigDecimal(-10.1504);
        System.out.println(b2.setScale(2,BigDecimal.ROUND_CEILING));//10.16
        System.out.println(b3.setScale(2,BigDecimal.ROUND_CEILING));//-10.15
        /**
         * BigDecimal.ROUND_FLOOR
         * 接近负无穷大的舍入模式
         * 如果 BigDecimal 为正,则舍入行为与 ROUND_DOWN 相同;
         　　如果为负,则舍入行为与 ROUND_UP 相同。
         　　注意,此舍入模式始终不会增加计算值
         */
        System.out.println(b2.setScale(2,BigDecimal.ROUND_FLOOR));//10.15
        System.out.println(b3.setScale(2,BigDecimal.ROUND_FLOOR));//-10.16
        /**
         *BigDecimal.ROUND_HALF_UP
         *如果舍弃部分 >= 0.5,则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同。
         * 正常的四舍五入
         */
        BigDecimal b4 = new BigDecimal(10.1554);
        BigDecimal b5 = new BigDecimal(10.1514);
        System.out.println(b4.setScale(2,BigDecimal.ROUND_HALF_UP));//10.16
        System.out.println(b5.setScale(2,BigDecimal.ROUND_HALF_UP));//10.15
        /**
         * BigDecimal.ROUND_HALF_DOWN
         * 如果舍弃部分 > 0.5,则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同(五舍六入
         */
        BigDecimal b6 = new BigDecimal(10.5);
        BigDecimal b7 = new BigDecimal(10.1554);
        System.out.println(b6.setScale(0,BigDecimal.ROUND_HALF_DOWN));//10
        System.out.println(b7.setScale(2,BigDecimal.ROUND_HALF_DOWN));//10.16
        /**
         * BigDecimal.ROUND_HALF_EVEN
         * 如果舍弃部分左边的数字为奇数,则舍入行为与 ROUND_HALF_UP 相同;
         　　如果为偶数,则舍入行为与 ROUND_HALF_DOWN 相同。
         　　注意,在重复进行一系列计算时,此舍入模式可以将累加错误减到最小。
         　　此舍入模式也称为“银行家舍入法”,主要在美国使用。四舍六入,五分两种情况。
         　　如果前一位为奇数,则入位,否则舍去。
         　　以下例子为保留小数点1位,那么这种舍入方式下的结果。
         　　1.35>1.4 1.25>1.2
         */
        BigDecimal b8 = new BigDecimal(1.35);
        BigDecimal b9 = new BigDecimal(1.25);
        System.out.println(b8.setScale(1,BigDecimal.ROUND_HALF_EVEN));//1.4
        System.out.println(b9.setScale(1,BigDecimal.ROUND_HALF_EVEN));//1.2
        /**
         * BigDecimal.ROUND_UNNECESSARY
         * 一般不用
         */
        System.out.println(b9.setScale(1,BigDecimal.ROUND_UNNECESSARY));
    }
}
