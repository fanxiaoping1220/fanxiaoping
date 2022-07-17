package com.xingkong.spingboot.net;

import java.net.*;

/**
 * * @className: UDPClient
 * * @description: UDP客户端 也称发送端
 * * 发送消息
 * * 不需要连接服务器
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 16:00
 **/
public class UDPClient {

    public static void main(String[] args) throws Exception{
        //1.建立一个socket
        DatagramSocket socket = new DatagramSocket();
        //2.建个包
        String msg = "你好啊,服务器";
        //3.发送给谁
        InetAddress ip = InetAddress.getByName("localhost");
        int port = 9000;
        //数据 数据长度起始 要发送给谁
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), 0, msg.getBytes().length, ip, port);
        //4.发送包
        socket.send(packet);
        //5.关闭流
        socket.close();
    }
}
