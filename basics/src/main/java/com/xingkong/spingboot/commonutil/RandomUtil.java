package com.xingkong.spingboot.commonutil;

import java.util.Random;

/**
 * * @className: RandomUtil
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/3/31 0031 16:04
 **/
public class RandomUtil {

    /**
     * 生成固定位数的随机数
     * @param len
     * @return
     */
    public static String random(int len){
        StringBuilder number = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }
}
