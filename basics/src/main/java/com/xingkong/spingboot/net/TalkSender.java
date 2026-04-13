package com.xingkong.spingboot.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * * @className: TalkSender
 * * @description: 聊天发送方
 * * @author: fan xiaoping
 * * @date: 2022/7/17 0017 17:47
 **/
public class TalkSender implements Runnable {
    DatagramSocket socket = null;
    BufferedReader reader = null;

    /**
     * 发送方的ip
     */
    private String toIp;

    /**
     * 发送方的端口号
     */
    private int toPort;

    /**
     * 谁发送的ip
     */
    private int fromPort;

    public TalkSender(String toIp, int toPort, int fromPort) {
        this.toIp = toIp;
        this.toPort = toPort;
        this.fromPort = fromPort;
        try {
            socket = new DatagramSocket(fromPort);
            reader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String sendData = reader.readLine();
                byte[] data = sendData.getBytes();
                DatagramPacket packet = new DatagramPacket(data,0,data.length,new InetSocketAddress(toIp,toPort));
                socket.send(packet);
                //退出 断开连接
                if ("bye".equals(new String(data))) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }
}
