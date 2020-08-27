package com.xingkong.spingboot.Test;

import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZMQ;

import java.time.LocalDateTime;

/**
 * * @className: ZMQ
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020-08-27  16:16
 **/
@Slf4j
public class ZMQTest {

    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
        subscriber.connect("tcp://192.168.2.213:20122");
        String filter = "{\"type\":\"bsm\"";
        subscriber.subscribe(filter.getBytes());
        int index = 0;
        log.info("激光雷达线程启动-------");
        while (true) {
            System.out.println("当前时间:"+ LocalDateTime.now());
            byte[] msg = subscriber.recv(0);
            String string = new String(msg);
            index++;
            log.info("Received: " + index + "激光雷达数据:" + string);
        }
    }
}
