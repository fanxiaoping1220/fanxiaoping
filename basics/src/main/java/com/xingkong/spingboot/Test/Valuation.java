package com.xingkong.spingboot.test;

/**
 * description:赋值
 * Created by 46926 on 2018/8/4.
 * 别名现象
 *
 * @author fanxiaoping
 */
public class Valuation {

    static class Tank {
        int level;
    }

    public static void main(String[] args) {
        Tank t1 = new Tank();
        t1.level = 10;
        Tank t2 = new Tank();
        t2.level = 100;
        System.out.println("t1 = " + t1.level + ";t2 = " + t2.level);//10,100
        t1 = t2;
        System.out.println("t1 = " + t1.level + ";t2 = " + t2.level);//100,100
        t1.level = 50;
        System.out.println("t1 = " + t1.level + ";t2 = " + t2.level);//50,50

        t1.level = t2.level;
        System.out.println("t1 = " + t1.level + ";t2 = " + t2.level);//50,50
        t2.level = 166;
        System.out.println("t1 = " + t1.level + ";t2 = " + t2.level);//166,166
        f(t1);
        System.out.println("t1 = " + t1.level + ";t2 = " + t2.level);//196,196
    }

    private static void f(Tank tank) {
        tank.level = 196;
    }
}
