package com.xingkong.spingboot.Test;

import com.xingkong.spingboot.entity.ThreadDome;

/**
 * @ClassName Test
 * @Description
 * @Author fanxiaoping
 * @Date 2018/9/21 11:13
 * @Version 1.0.0
 **/
public class Test {

    public static void main(String[] args) {
        ThreadDome td = new ThreadDome();
        new  Thread(td).start();
        while(true){
            if(td.isFlag()){
                System.out.println("~~~~~~~~~~~~~~~~~~~~~");
                break;
            }
        }
    }
}
