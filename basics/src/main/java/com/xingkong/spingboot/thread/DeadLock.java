package com.xingkong.spingboot.thread;

/**
 * * @className: DeadLock
 * * @description: 死锁
 * * 多个线程互相抱着对方需要的资源，然后形成僵持
 * * @author: fan xiaoping
 * * @date: 2022/6/18 0018 22:11
 **/
public class DeadLock {

    public static void main(String[] args) {
        Makeup g1 = new Makeup(0,"灰姑娘");
        Makeup g2 = new Makeup(1,"白雪公主");
        g1.start();
        g2.start();
    }
}

/**
 * 口红
 */
class Lipstick{

}

/**
 * 镜子
 */
class Mirror{

}

/**
 * 化妆
 */
class Makeup extends Thread{
    //需要的资源只有一份，用static修饰保证资源只有一份
    static Lipstick lipstick = new Lipstick();

    static Mirror mirror = new Mirror();

    /**
     * 选择
     */
    public int choice;

    /**
     * 女生的名字
     */
    public String girlName;

    public Makeup(int choice, String girlName){
        this.choice = choice;
        this.girlName = girlName;
    }


    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始化妆
     * 互相持有对方需要的锁,就是需要拿到对方的资源
     * @throws InterruptedException
     */
    private void makeup() throws InterruptedException {
        if(choice == 0){
            //获得口红的锁
            synchronized (lipstick){
                System.out.println(this.girlName+"拿到了口红的锁!");
                Thread.sleep(1000);
                //获得镜子的锁
                synchronized (mirror){
                    System.out.println(this.girlName+"拿到了镜子的锁!");
                }
            }
        }else {
            //获得镜子的锁
            synchronized (mirror){
                System.out.println(this.girlName+"拿到了镜子的锁!");
                Thread.sleep(2000);
                //获得口红的锁
                synchronized (lipstick){
                    System.out.println(this.girlName+"拿到了口红的锁!");
                }
            }

        }
    }
}