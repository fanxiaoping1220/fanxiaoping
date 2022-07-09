package com.xingkong.spingboot.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * * @className: TcpClient
 * * @description: 客户端 模拟聊天
 * * @author: fan xiaoping
 * * @date: 2022/7/9 0009 16:52
 **/
public class TcpClient {

    public static void main(String[] args) {
        Socket socket = null;
        OutputStream send = null;
        try {
            //1.要连接服务器的地址和端口号
            InetAddress ip =  InetAddress.getByName("127.0.0.1");
            int port = 9520;
            //2.创建socket连接
            socket = new Socket(ip,port);
            //3.发送消息io流
            send = socket.getOutputStream();
            send.write("你好，欢迎学习狂神说Java".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //4.关闭流
            if(send != null){
                try {
                    send.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
