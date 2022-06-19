package com.xingkong.spingboot.thread;

/**
 * * @className: TestPC
 * * @description: 生产者消费者模型,利用缓冲区解决：管程法
 * * 生产者 消费者 产品 缓冲区
 * * @author: fan xiaoping
 * * @date: 2022/6/19 0019 16:24
 **/
public class TestPC {

    public static void main(String[] args) {

        SynCache synCache = new SynCache();
        new Produce(synCache).start();
        new Consumer(synCache).start();
    }
}

/**
 * 生产者
 */
class Produce extends Thread {
    public SynCache synCache;

    public Produce (SynCache synCache){
        this.synCache = synCache;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            synCache.push(new Goods(i));
            System.out.println("生产者生成了第"+i+"鸡");
        }
    }
}
/**
 * 消费者
 */
class  Consumer extends Thread{

    public SynCache synCache;

    public Consumer (SynCache synCache){
        this.synCache = synCache;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费者消费了第"+synCache.pop().id+"只鸡");
        }
    }
}

/**
 * 产品
 */
class Goods{

    public int id;

    public Goods(int id) {
        this.id = id;
    }
}

/**
 * 缓冲区
 */
class SynCache{

    //需要一个容器大小
    public Goods[] goodsList = new Goods[10];
    //计数器
    public int count = 0;

    //生产者生产产品
    public synchronized void push (Goods goods){
        //如果容器满了,需要等待消费者消费
        if(count == goodsList.length){
            //通知消费者消费，生产者等待
            System.out.println("通知消费者消费，生产者等待");
            try {
                //生产者等待
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //如果容器没有满,需要丢入产品
        goodsList[count] = goods;
        count++;
        //可以通知消费者消费了
        this.notifyAll();
    }

    /**
     * 消费者消费产品
     * @return
     */
    public synchronized Goods pop(){
        //判断是否有产品
        if(count == 0){
            //通知生产者生成，消费者等待
            System.out.println("通知生产者生成，消费者等待");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //消费者消费产品
        count--;
        Goods goods = goodsList[count];
        this.notifyAll();
        return goods;
    }

}
