package com.xingkong.spingboot.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * * @className: TcpFileUploadClient
 * * @description: tcp 实现文件上传 客户端
 * * @author: fan xiaoping
 * * @date: 2022/7/10 0010 16:26
 **/
public class TcpFileUploadClient {

    public static void main(String[] args) throws Exception {
        File file = new File("image"+File.separator+"微信图片_20220709144045.jpg");
        //1.创建一个socket连接
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9000);
        //2.创建一个输出流
        OutputStream outputStream = socket.getOutputStream();
        //3.读取文件
        FileInputStream fileInputStream = new FileInputStream(file);
        //4.写出文件
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fileInputStream.read(buffer)) != -1) {
            outputStream.write(buffer,0,len);
        }
        //5.关闭流
        fileInputStream.close();
        outputStream.close();
        socket.close();
    }
}
