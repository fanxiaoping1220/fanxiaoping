package com.xingkong.spingboot.net;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * * @className: URL
 * * @description: URL 解析
 * * @author: fan xiaoping
 * * @date: 2022/7/21 0021 11:22
 **/
public class URLTest {

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://localhost:8080/helloword/index.jsp?username=xingchen&password=123");
        //协议
        System.out.println(url.getProtocol());
        //端口
        System.out.println(url.getPort());
        //主机ip
        System.out.println(url.getHost());
        //文件
        System.out.println(url.getFile());
        //全路径
        System.out.println(url.getPath());
        //参数
        System.out.println(url.getQuery());
    }
}
