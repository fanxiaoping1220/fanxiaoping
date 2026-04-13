package com.xingkong.spingboot.net;

import java.net.InetSocketAddress;

/**
 * * @className: TestInetSocketAddress
 * * @description: 端口测试
 * * @author: fan xiaoping
 * * @date: 2022/6/26 0026 21:45
 **/
public class TestInetSocketAddress {

    public static void main(String[] args) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 3306);
        InetSocketAddress inetSocketAddress2 = new InetSocketAddress("localhost", 3306);
        System.out.println(inetSocketAddress);
        System.out.println(inetSocketAddress2);

        System.out.println(inetSocketAddress.getAddress());
        System.out.println(inetSocketAddress.getHostName());
        System.out.println(inetSocketAddress.getPort());
        System.out.println(inetSocketAddress.getHostString());
    }
}
