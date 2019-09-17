package com.xingkong.spingboot.controller;

import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.xingkong.spingboot.entity.AlipayBillDetailDTO;
import com.xingkong.spingboot.service.bill.service.AlipayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * @throws AlipayApiException
     * @throws IOException
     */
    @PostMapping(value = "/getYesterdayBill")
    String getYesterdayBill() throws AlipayApiException, IOException {
        return alipayBillService.getYesterdayBill();
    }

    /**
     * 查询支付宝账单明细
     * @param alipayBillDetailDTO
     * @return
     */
    @GetMapping(value = "/getList")
    JSONArray getList(@RequestBody AlipayBillDetailDTO alipayBillDetailDTO){
        return alipayBillService.getList(alipayBillDetailDTO);
    }
}