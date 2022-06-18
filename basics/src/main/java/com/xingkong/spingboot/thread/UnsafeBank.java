package com.xingkong.spingboot.thread;

/**
 * * @className: UnsafeBank
 * * @description: 模拟取钱
 * * 不安全的取钱
 * * 两个人同时去银行取钱，同一个账号
 * * 加上synchronize(){}同步代码块变成线程安全的了
 * * @author: fan xiaoping
 * * @date: 2022/6/18 0018 17:15
 **/
public class UnsafeBank {

    public static void main(String[] args) {
        UserAccount userAccount = new UserAccount(100,"结婚基金");

        Drawing you = new Drawing(userAccount,50,"你");
        Drawing girlFriend = new Drawing(userAccount,100,"老婆");

        you.start();
        girlFriend.start();
    }
}

/**
 * 用户账号
 */
class UserAccount{

    /**
     * 余额
     */
    public double money;

    /**
     * 卡名
     */
    public String name;

    public UserAccount(double money, String name) {
        this.money = money;
        this.name = name;
    }
}

class Drawing extends Thread{

    /**
     * 用户账号
     */
    private UserAccount userAccount;

    /**
     * 取多少钱
     */
    private double drawingMoney;

    /**
     * 现在手里有多少钱
     */
    private double nowMoney;

    public Drawing(UserAccount userAccount,double drawingMoney,String name){
        super(name);
        this.userAccount = userAccount;
        this.drawingMoney = drawingMoney;
    }

    /**
     * 取钱
     */
    @Override
    public void run() {
        //锁的对象是变化的量,需要增删改的对象
        synchronized (this.userAccount){
            //判读是否有钱
            if(userAccount.money - drawingMoney < 0){
                System.out.println(Thread.currentThread().getName()+"钱不够，取不了，余额为"+userAccount.money);
                return;
            }

            try {
                //sleep可以放大问题的发生性
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //卡里余额
            userAccount.money = userAccount.money - drawingMoney;
            // 手里的钱
            nowMoney += drawingMoney;

            System.out.println(userAccount.name+ "余额为"+ userAccount.money);
            // Thread.currentThread().getName() = this.getName() 因为继承了Thread
            System.out.println(this.getName()+"手里的钱为"+nowMoney);
        }
    }
}
