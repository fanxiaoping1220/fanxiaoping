package com.xingkong.spingboot.service.bill.service;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.xingkong.spingboot.controller.bill.dto.AlipayBillDetailDTO;

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
     * @throws AlipayApiException
     * @throws IOException
     */
    String getYesterdayBill() throws AlipayApiException, IOException;

    /**
     * 查询支付宝账单明细
     * 条件：昨天的账单, 3天的账单, 一个星期的账单
     * @param alipayBillDetailDTO
     * @return
     */
    JSONArray getList(AlipayBillDetailDTO alipayBillDetailDTO);
}
