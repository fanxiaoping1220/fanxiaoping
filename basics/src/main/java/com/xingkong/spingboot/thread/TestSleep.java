package com.xingkong.spingboot.thread;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * * @className: TestSleep
 * * @description: 模拟倒计时
 * * @author: fan xiaoping
 * * @date: 2022/6/11 0011 23:06
 **/
public class TestSleep {

    public static void main(String[] args) {
        //获取当前时间
        LocalDateTime date = LocalDateTime.now();
        while (true){
            try {
                Thread.sleep(1000);
                System.out.println(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US)));
                //更新当前时间
                date = LocalDateTime.now();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
