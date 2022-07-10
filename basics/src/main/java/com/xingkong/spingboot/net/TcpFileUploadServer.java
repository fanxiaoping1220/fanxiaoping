package com.xingkong.spingboot.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * * @className: TcpFileUploadServer
 * * @description: tcp 实现文件上传 服务端
 * * @author: fan xiaoping
 * * @date: 2022/7/10 0010 16:26
 **/
public class TcpFileUploadServer {

    public static void main(String[] args) throws Exception {
        //1.创建连接
        ServerSocket serverSocket = new ServerSocket(9000);
        //2.监听客户端的连接
        Socket socket = serverSocket.accept();
        //3.获取输入流
        InputStream inputStream = socket.getInputStream();
        //4.文件输出
        FileOutputStream outputStream = new FileOutputStream("image"+File.separator+"receive.jpg");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1){
            outputStream.write(buffer,0,len);
        }
        //4.关闭流
        outputStream.close();
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
