package com.xingkong.spingboot.service.bill.service;

import com.alipay.api.AlipayApiException;

import java.io.IOException;

/**
 * @className: AlipayBillService
 * @description: 支付宝账单实现类
 * @author: 范小平
 * @date: 2019-09-10 10:57
 * @version: 1.0.0
 */
public interface AlipayBillService {

    /**
     * 获取支付宝昨日的账单
     * @return
     */
    String getYesterdayBill() throws AlipayApiException, IOException;
}
