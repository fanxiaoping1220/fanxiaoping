package com.xingkong.spingboot.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * * @className: UDPChatSender
 * * @description: 聊天发送端
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 16:43
 **/
public class UDPChatSender {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(8888);
        //准备发送数据
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            String sendData = reader.readLine();
            byte[] data = sendData.getBytes();
            DatagramPacket packet = new DatagramPacket(data,0,data.length,new InetSocketAddress("127.0.0.1",9000));
            socket.send(packet);
            //退出 断开连接
            if("bye".equals(data)){
                break;
            }
        }
        socket.close();
    }
}
