package com.xingkong.spingboot.thread;

/**
 * * @className: SimulationUserBuyTickets
 * * @description: 模拟用户买票
 * * 不安全的买票 线程不安全
 * * @author: fan xiaoping
 * * @date: 2022/6/18 0018 16:53
 **/
public class SimulationUserBuyTickets {

    public static void main(String[] args) {
        Tickets tickets = new Tickets();
        Thread thread = new Thread(tickets,"牛逼的我");
        Thread thread2 = new Thread(tickets,"苦逼的你们");
        Thread thread3 = new Thread(tickets,"可恶的黄牛");
        thread.start();
        thread2.start();
        thread3.start();
    }
}

class Tickets implements Runnable{

    /**
     * 总票数
     */
    private int TicketsNum = 10;

    /**
     * 线程停止方式
     */
    private boolean flag = true;

    @Override
    public void run() {
        //买票
        while (flag){
            try {
                buy();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * synchronized
     * 加上同步锁 变成线程安全的了
     * @throws InterruptedException
     */
    private synchronized void buy() throws InterruptedException {
        //是否有票
        if(TicketsNum <= 0){
            flag = false;
            return;
        }
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+"拿到票"+TicketsNum--);
    }
}
