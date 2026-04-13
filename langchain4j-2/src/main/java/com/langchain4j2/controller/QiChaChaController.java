package com.langchain4j2.controller;

import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 企查查接口
 */
@Slf4j
@RequestMapping
@RestController
public class QiChaChaController {

    public static void main(String[] args) {
        //key+Timespan+SecretKey
        long dateTime = System.currentTimeMillis()/1000;
        log.info("dateTime:{}",dateTime);
        String data = "8b8c9e6e5a8e45e1ad06339aa7d971f1".concat(String.valueOf(dateTime)).concat("BF2B7A41D1B0081E1A98C48B70FBC216");
        String encrypt = DigestUtil.md5Hex(data).toUpperCase();
        log.info("encrypt:{}",encrypt);
    }
}
