package com.xingkong.spingboot.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * * @className: UDPServer
 * * @description: UDP 服务端 也称接收端
 * * 发送消息
 * * 还是要等待客户端的连接
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 16:13
 **/
public class UDPServer {

    public static void main(String[] args) throws Exception {
        //1.开放端口
        DatagramSocket socket = new DatagramSocket(9000);
        //2.接收数据包
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        //3.接收
        //阻塞接收
        socket.receive(packet);
        System.out.println(packet.getAddress().getHostAddress());
        System.out.println(new String(packet.getData()));
        //4.关闭连接
        socket.close();
    }
}
