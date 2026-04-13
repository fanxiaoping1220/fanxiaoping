package com.xingkong.spingboot.commonutil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.v3.util.PemUtils;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import com.xingkong.spingboot.config.WxPayProperties;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * * @className: WxPayV3Util
 * * @description:微信支付v3工具类
 * * @author: fan xiaoping
 * * @date: 2023/3/28 0028 15:03
 **/
@Component
public class WxPayV3Util {

    private static final String PAY_BASE_URL = "https://api.mch.weixin.qq.com";

    public static JSONObject unifiedOrderV3(String reqUrl, JSONObject reqJSON, WxPayService wxPayService) throws WxPayException {
        String response = wxPayService.postV3(PAY_BASE_URL + reqUrl, reqJSON.toJSONString());
        return JSONObject.parseObject(getPayInfo_(response, wxPayService.getConfig()));
    }

    public static String getPayInfo_(String response, WxPayConfig wxPayConfig)  throws WxPayException {
        try {
            JSONObject resJSON = JSON.parseObject(response);
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
            String nonceStr = SignUtils.genRandomStr();
            String prepayId = resJSON.getString("prepay_id");

            Map<String, String> payInfo = new HashMap<>(8);

            String appid = wxPayConfig.getAppId();

            payInfo.put("appId", appid);
            payInfo.put("timeStamp", timestamp);
            payInfo.put("nonceStr", nonceStr);
            payInfo.put("package", "prepay_id=" + prepayId);
            payInfo.put("signType", "RSA");

            String beforeSign = String.format("%s\n%s\n%s\n%s\n", appid, timestamp, nonceStr, "prepay_id=" + prepayId);
            payInfo.put("paySign", SignUtils.sign(beforeSign, PemUtils.loadPrivateKey(new FileInputStream(wxPayConfig.getPrivateKeyPath()))));
            // 签名以后在增加prepayId参数
            payInfo.put("prepayId", prepayId);
            return JSON.toJSONString(payInfo);
        } catch (Exception e) {
            throw (e instanceof WxPayException) ? (WxPayException) e : new WxPayException(e.getMessage(), e);
        }
    }

    /**
     * 提现至微信零钱
     * @param openid openid
     * @param realName 用户昵称
     * @param amount 金额
     * @param wxPayProperties 微信配置参数
     * @return
     */
    public static EntPayResult depositSmallChange(String openid, String realName, Integer amount, WxPayProperties wxPayProperties) throws WxPayException {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setMchId(wxPayProperties.getMchId());
        wxPayConfig.setMchKey(wxPayProperties.getMchKey());
        wxPayConfig.setAppId(wxPayProperties.getAppId());
        wxPayConfig.setApiV3Key(wxPayProperties.getApiV3Key());
        wxPayConfig.setCertSerialNo(wxPayProperties.getCertSerialNo());
        wxPayConfig.setKeyPath(wxPayProperties.getKeyPath());
        wxPayConfig.setPrivateCertPath(wxPayProperties.getPrivateCertPath());
        wxPayConfig.setPrivateKeyPath(wxPayProperties.getPrivateKeyPath());
        wxPayConfig.setTradeType(WxPayConstants.TradeType.JSAPI);
        wxPayConfig.setSignType(WxPayConstants.SignType.MD5);
        WxPayService wxPayService = new WxPayServiceImpl();
        //微信配置信息
        wxPayService.setConfig(wxPayConfig);
        EntPayRequest entPayRequest = new EntPayRequest();
        entPayRequest.setAppid(wxPayProperties.getAppId());
        entPayRequest.setMchId(wxPayProperties.getMchId());
        entPayRequest.setNonceStr(SignUtils.genRandomStr());
        String partnerTradeNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+ RandomUtil.random(4);
        entPayRequest.setPartnerTradeNo(partnerTradeNo);
        entPayRequest.setOpenid(openid);
        entPayRequest.setCheckName(WxPayConstants.CheckNameOption.NO_CHECK);
        entPayRequest.setAmount(amount);
        entPayRequest.setDescription(realName+"作者收益提现");
        entPayRequest.setSpbillCreateIp("182.92.72.188");
        EntPayResult entPayResult = wxPayService.getEntPayService().entPay(entPayRequest);
        return entPayResult;
    }

}
