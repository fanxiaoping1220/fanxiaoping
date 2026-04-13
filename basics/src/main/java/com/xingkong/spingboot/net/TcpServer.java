package com.xingkong.spingboot.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * * @className: TcpServer
 * * @description: 服务端 模拟聊天
 * * @author: fan xiaoping
 * * @date: 2022/7/9 0009 16:52
 **/
public class TcpServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream inputStream = null;
        ByteArrayOutputStream output = null;
        try {
            //1.我的有一个地址
            serverSocket = new ServerSocket(9520);
            while (true){
                //2.等待客户端连接
                socket = serverSocket.accept();
                //3.读取客户端的消息
                inputStream = socket.getInputStream();
                output = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len ;
                while ((len = inputStream.read(buffer)) != -1) {
                    output.write(buffer,0,len);
                }
                System.out.println(output.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //4.关闭流
            if(output != null){
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream != null){
                try {
                    inputStream.close();
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
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
