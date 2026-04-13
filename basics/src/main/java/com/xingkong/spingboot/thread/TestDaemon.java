package com.xingkong.spingboot.thread;

/**
 * * @className: TestDaeMon
 * * @description:  测试守护线程
 * * 上帝守护着你
 * * @author: fan xiaoping
 * * @date: 2022/6/17 0017 23:34
 **/
public class TestDaemon {

    public static void main(String[] args) {
        Thread gob = new Thread(new God());
        //默认false是用户线程，正常的线程都是用户线程
        gob.setDaemon(true);
        gob.start();
        Thread you = new Thread(new You());
        you.start();
    }

}

/**
 * 你
 */
class You implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 36500; i++) {
            System.out.println("每天过的很开心!");
        }
        System.out.println("goodBye world");
    }
}

/**
 * 上帝
 */
class God implements Runnable{

    @Override
    public void run() {
        while (true){
            System.out.println("上帝保佑着你!");
        }
    }
}
