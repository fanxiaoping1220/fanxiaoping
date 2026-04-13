package com.xingkong.spingboot.thread;

/**
 * * @className: Race
 * * @description: 模拟龟兔赛跑
 * * 兔子需要睡觉，结果乌龟赢
 * * @author: fan xiaoping
 * * @date: 2022/6/3 0003 20:55
 **/
public class Race implements Runnable{

    private static  String winner ;

    @Override
    public void run() {
        System.out.println("比赛开始：----------------------------");
        for (int i = 0; i <= 100; i++) {
            //没走100步休息10ms
            if(Thread.currentThread().getName().equals("rabbit") && i % 10 == 0){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boolean flag = gameOver(i);
            if (flag) {
                break;
            }
            System.out.println("当前"+Thread.currentThread().getName()+"距离终点还有多少米："+(100-i));
        }
    }

    /**
     * 判断游戏是否结束
     * @param i
     * @return
     */
    private boolean gameOver(int i){
        if(winner != null){
            return true;
        }
        if(i == 100){
            winner = Thread.currentThread().getName();
            System.out.println("胜利者为："+winner);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        new Thread(new Race(),"rabbit").start();
        new Thread(new Race(),"tortoise").start();
    }
}
