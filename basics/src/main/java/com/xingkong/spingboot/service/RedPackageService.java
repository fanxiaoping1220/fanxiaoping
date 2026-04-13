package com.xingkong.spingboot.service;

/**
 * * @className: RedPackageService
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2024/4/29 0029 16:50
 **/
public interface RedPackageService {

    /**
     * 发红包
     * @param totalMoney 总金额
     * @param redPackageNumber 总个数
     * @return
     */
    String sendRedPackage(int totalMoney,int redPackageNumber);

    /**
     * 抢红包
     * @param redPackageKey 抢红包的key
     * @param userId 用户id
     * @return
     */
    String robRedPackage(String redPackageKey,String userId);
}
