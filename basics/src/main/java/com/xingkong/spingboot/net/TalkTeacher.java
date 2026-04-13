package com.xingkong.spingboot.net;

/**
 * * @className: TalkTeacher
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 18:14
 **/
public class TalkTeacher {

    public static void main(String[] args) {
        //开启2个线程
        new Thread(new TalkSender("localhost",8000,6666)).start();
        new Thread(new TalkReceive(9000,"学生")).start();
    }
}
