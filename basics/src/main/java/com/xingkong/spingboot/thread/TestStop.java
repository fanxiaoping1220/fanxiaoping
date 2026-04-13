package com.xingkong.spingboot.thread;

/**
 * * @className: TestStop
 * * @description: 测试线程停止stop
 * * 1.建议线程正常停止----利用次数，不建议死循环
 * * 2.建议使用标志位，----> 设置一个标志位
 * * 3.不要使用stop或者 destory等过时或者JDK不建议使用的方法
 * * @author: fan xiaoping
 * * @date: 2022/6/9 0009 22:15
 **/
public class TestStop implements Runnable{

    //设置标位
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag){
            System.out.println("run ------------------Thread"+i++);
        }
    }

    /**
     * 停止方法
     */
    public void stop(){
        this.flag = false;
    }

    public static void main(String[] args) {
        TestStop testStop = new TestStop();
        new Thread(testStop).start();
        for (int i = 0; i < 1000; i++) {
            System.out.println("main i="+i);
            if(i==900){
                //切换标致位，让线程停止
                testStop.stop();
                System.out.println("该线程停止了"+"i="+i);
            }
        }
    }
}
