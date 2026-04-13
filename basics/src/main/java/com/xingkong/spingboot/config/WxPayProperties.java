package com.xingkong.spingboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * * @className: WxPayProperties
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/3/31 0031 15:56
 **/
@ConfigurationProperties(prefix = "wxpay")
@Component
@Data
public class WxPayProperties {

    private String mchId;

    private String appId;

    private String mchKey;

    private String apiV3Key;

    private String certSerialNo;

    /**
     * 证书c12路径
     */
    private String keyPath;

    /**
     * cert路径
     */
    private String privateCertPath;

    /**
     * key路径
     */
    private String privateKeyPath;
}
