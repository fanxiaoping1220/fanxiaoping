package com.xingkong.spingboot.thread;

/**
 * * @className: TestState
 * * @description: 测试线程状态
 * * Thread.State.NEW; 创建线程
 * * Thread.State.RUNNABLE; 可运行状态
 * * Thread.State.BLOCKED; 阻塞
 * * Thread.State.TIMED_WAITING; 时间等待 正在等待另一个线程执行动作达到指定等待时间的线程处于此状态。
 * * Thread.State.WAITING; 等待 正在等待另一个线程执行特定动作的线程处于此状态
 * * Thread.State.TERMINATED; 死亡 结束
 * * @author: fan xiaoping
 * * @date: 2022/6/14 0014 23:19
 **/
public class TestState {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    //线程阻塞
                    Thread.sleep(1000);
                    System.out.println("i="+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //获取线程状态
        Thread.State state = thread.getState();
        System.out.println(state);//new

        thread.start();
        //更新线程状态
        state = thread.getState();
        System.out.println(state);//run

        //只要线程不终止就一直输出状态
        while (state != Thread.State.TERMINATED){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //更新线程状态
            state = thread.getState();
            System.out.println(state);
        }
        //线程终断或者结束，一旦进入死亡状态，就不能再次启动
//        thread.start();

    }
}
