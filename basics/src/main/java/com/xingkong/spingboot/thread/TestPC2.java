package com.xingkong.spingboot.thread;

/**
 * * @className: TestPC2
 * * @description: 信号灯法，标志位解决
 * * @author: fan xiaoping
 * * @date: 2022/6/21 0021 22:46
 **/
public class TestPC2 {

    public static void main(String[] args) {
        TV tv = new TV();
        new Actor(tv).start();
        new Audience(tv).start();
    }
}

/**
 * 生产者 演员
 */
class Actor extends Thread{

    public TV tv;

    public Actor(TV tv){
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            if(i % 2 == 0){
                tv.play("快乐大本营播放中");
            }else {
                tv.play("抖音：记录美好生活");
            }
        }
    }
}

/**
 * 消费者 观众
 */
class Audience extends Thread{

    public TV tv;

    public Audience(TV tv){
        this.tv = tv;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            tv.watch();
        }
    }
}

/**
 * 产品 节目
 */
class TV{
    //演员表演 观众等待
    //观众观看 演员等待
    //表演的节目
    public String voice;
    public boolean flag = true;
    //表演
    public synchronized void play(String voice){
        if(!flag){
            //演员等待表演
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("演员开始表演节目："+voice);
        //通知观众观看
        this.notifyAll();
        this.voice = voice;
        this.flag = !this.flag;
    }
    //观看
    public synchronized void watch(){
        if(flag){
            //观众等待演员表演
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("观众开始观看节目："+this.voice);
        //通知演员表演
        this.notifyAll();
        this.flag = !this.flag;
    }

}