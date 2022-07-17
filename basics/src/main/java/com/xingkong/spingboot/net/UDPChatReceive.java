package com.xingkong.spingboot.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * * @className: UDPChatReceive
 * * @description: 聊天接收端
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 16:44
 **/
public class UDPChatReceive {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9000);
        while (true){
            //准备接收包裹
            byte[] container = new byte[1024];
            DatagramPacket packet = new DatagramPacket(container,0,container.length);
            //阻塞式接收包裹
            socket.receive(packet);
            String receiveData = new String(packet.getData());
            System.out.println(receiveData);
            //断开连接 bye
            if("bye".equals(receiveData)){
                break;
            }
        }
        socket.close();
    }

}
