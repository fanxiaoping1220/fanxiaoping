package com.xingkong.spingboot.entity;

/**
 * @ClassName ThreadDome
 * @Description
 * @Author fanxiaoping
 * @Date 2018/9/21 11:10
 * @Version 1.0.0
 **/
public class ThreadDome implements Runnable {

    private boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (Exception e) {

        }
        flag = true;
        System.out.println("flag=" + flag);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
