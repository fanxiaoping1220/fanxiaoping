package com.xingkong.spingboot.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * * @className: TalkRecevice
 * * @description:聊天接收方
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 17:58
 **/
public class TalkReceive implements Runnable {
    DatagramSocket socket = null;

    /**
     * 来源端口号
     */
    private int fromPort;

    /**
     * 来源msg
     */
    private String fromMsg;

    public TalkReceive(int fromPort, String fromMsg) {
        this.fromPort = fromPort;
        this.fromMsg = fromMsg;
        try {
            socket = new DatagramSocket(fromPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                //准备接收包裹
                byte[] container = new byte[1024];
                DatagramPacket packet = new DatagramPacket(container,0,container.length);
                //阻塞式接收包裹
                socket.receive(packet);
                String receiveData = new String(packet.getData());
                System.out.println(fromMsg+":"+receiveData);
                //断开连接 bye
                if("bye".equals(receiveData)){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
