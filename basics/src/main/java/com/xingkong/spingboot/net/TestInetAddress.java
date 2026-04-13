package com.xingkong.spingboot.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * * @className: TestInetAddress
 * * @description: ip 测试
 * * @author: fan xiaoping
 * * @date: 2022/6/26 0026 21:01
 **/
public class TestInetAddress {

    public static void main(String[] args) {
        try {
            //本机
            InetAddress ip = InetAddress.getByName("127.0.0.1");
            System.out.println(ip);
            InetAddress localhost = InetAddress.getByName("localhost");
            System.out.println(localhost);
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println(localHost);
            //常用方法
            System.out.println(localHost.getAddress());
            System.out.println(localHost.getHostName());
            System.out.println(localHost.getHostAddress());
            System.out.println(localHost.getCanonicalHostName());
            //京东ip 39.175.102.3
            InetAddress jd = InetAddress.getByName("www.jd.com");
            System.out.println(jd);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
