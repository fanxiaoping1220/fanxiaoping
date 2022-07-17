package com.xingkong.spingboot.net;

/**
 * * @className: TalkStudent
 * * @description: 谈话学生端
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 18:09
 **/
public class TalkStudent {

    public static void main(String[] args) {
        new Thread(new TalkSender("localhost",9000,7777)).start();
        new Thread(new TalkRecevice(8000,"老师")).start();
    }
}
