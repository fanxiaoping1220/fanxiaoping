package com.xingkong.spingboot.controller;

import com.alipay.api.AlipayApiException;
import com.xingkong.spingboot.service.bill.service.AlipayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @className: AlipayBillController
 * @description: 支付宝账单API
 * @author: 范小平
 * @date: 2019-09-10 10:47
 * @version: 1.0.0
 */
@RestController
@RequestMapping(value = "/alipay/bill")
public class AlipayBillController {

    @Autowired
    private AlipayBillService alipayBillService;

    /**
     * 获取支付宝昨日的账单
     * @return
     */
    @PostMapping(value = "/getYesterdayBill")
    String getYesterdayBill() throws AlipayApiException, IOException {
        return alipayBillService.getYesterdayBill();
    }

}