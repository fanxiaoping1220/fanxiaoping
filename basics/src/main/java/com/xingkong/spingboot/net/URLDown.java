package com.xingkong.spingboot.net;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

/**
 * * @className: URLDown
 * * @description: url 下载资源
 * * @author: fan xiaoping
 * * @date: 2022/7/21 0021 11:34
 **/
public class URLDown {
    public static void main(String[] args) throws IOException {
        //1.下载地址
        URL url = new URL("https://y.qq.com/music/photo_new/T002R300x300M000003Cqllk4IoFoW.jpg?max_age=2592000");

        //2.连接到这个资源
        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream("image" + File.separator + "Cqllk4IoFoW.jpg");
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1){
            fileOutputStream.write(buffer,0,len);
        }

        //3.关闭资源
        fileOutputStream.close();
        inputStream.close();
        //端口连接
        urlConnection.disconnect();
    }
}
