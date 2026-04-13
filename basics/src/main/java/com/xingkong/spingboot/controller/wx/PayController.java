package com.xingkong.spingboot.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayResponse;
import com.xingkong.spingboot.commonutil.RandomUtil;
import com.xingkong.spingboot.commonutil.WxPayV3Util;
import com.xingkong.spingboot.config.WxPayProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * * @className: PayController
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/3/28 0028 15:00
 **/
@RequestMapping(value = "/wx/pay")
@RestController
@Slf4j
public class PayController {

    /**
     * v3支付url
     * 官方接口文档地址- https://pay.weixin.qq.com/wiki/doc/apiv3_partner/apis/chapter4_1_1.shtml
     */
    public static final String PAY_V3_URL = "/v3/pay/transactions/jsapi";

    @Autowired
    private WxPayProperties wxPayProperties;


    @PostMapping(value = "/planA")
    public JSONObject planA(){
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setMchId(wxPayProperties.getMchId());
        wxPayConfig.setAppId(wxPayProperties.getAppId());
        wxPayConfig.setMchKey(wxPayProperties.getMchKey());
        wxPayConfig.setApiV3Key(wxPayProperties.getApiV3Key());
        wxPayConfig.setCertSerialNo(wxPayProperties.getCertSerialNo());
        wxPayConfig.setKeyPath(wxPayProperties.getKeyPath());
        wxPayConfig.setPrivateCertPath(wxPayProperties.getPrivateCertPath());
        wxPayConfig.setPrivateKeyPath(wxPayProperties.getPrivateKeyPath());
        // 小程序支付
        wxPayConfig.setTradeType(WxPayConstants.TradeType.JSAPI);
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(wxPayConfig); //微信配置信息

        try {
            JSONObject jsonObject = WxPayV3Util.unifiedOrderV3(PAY_V3_URL, param(), wxPayService);
            log.info("支付返回：{}", jsonObject);
            return jsonObject;
        } catch (WxPayException e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error",e.getMessage());
            return jsonObject;
        }
    }

    private static JSONObject param() {
        String payOrderId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+ RandomUtil.random(4);

        // 微信统一下单请求对象
        JSONObject reqJSON = new JSONObject();
        reqJSON.put("out_trade_no", payOrderId);
        reqJSON.put("description", "我是一个商品");
        // 订单失效时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE,示例值：2018-06-08T10:34:56+08:00
//        reqJSON.put("time_expire", String.format("%sT%s+08:00", DateUtil.format(orderPay.getExpiredTime(), DatePattern.NORM_DATE_FORMAT), DateUtil.format(orderPay.getExpiredTime(), DatePattern.NORM_TIME_FORMAT)));

        reqJSON.put("notify_url", "https://www.baidu.com");
        JSONObject amount = new JSONObject();
        amount.put("total", 1);
        amount.put("currency", "CNY");
        reqJSON.put("amount", amount);

//        JSONObject sceneInfo = new JSONObject();
//        sceneInfo.put("payer_client_ip", "172.130.100.39");
//        reqJSON.put("scene_info", sceneInfo);

        reqJSON.put("appid", "wxa11729a79b0e847e");
        reqJSON.put("mchid", "1376395802");

        JSONObject payer = new JSONObject();
        payer.put("openid", "o-M6O4mMzhvBycpxtu1nXem2Rqh0");
        reqJSON.put("payer", payer);

        return reqJSON;
    }

    @PostMapping(value = "/planB")
    public PrepayResponse planB(){
        String payOrderId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))+ RandomUtil.random(4);
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config =
                new RSAAutoCertificateConfig.Builder()
                        .merchantId(wxPayProperties.getMchId())
                        .privateKeyFromPath(wxPayProperties.getPrivateKeyPath())
                        .merchantSerialNumber(wxPayProperties.getCertSerialNo())
                        .apiV3Key(wxPayProperties.getApiV3Key())
                        .build();
        JsapiService service = new JsapiService.Builder().config(config).build();
        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        amount.setTotal(1);
        request.setAmount(amount);
        request.setAppid(wxPayProperties.getAppId());
        request.setMchid(wxPayProperties.getMchId());
        request.setDescription("测试商品标题");
        request.setNotifyUrl("https://notify_url");
        request.setOutTradeNo(payOrderId);
        Payer payer = new Payer();
        payer.setOpenid("o-M6O4mMzhvBycpxtu1nXem2Rqh0");
        request.setPayer(payer);
        PrepayResponse response = service.prepay(request);
        System.out.println(response.getPrepayId());
        return response;
    }

    /**
     * 微信提现至零钱
     */
    @PostMapping(value = "/depositSmallChange")
    public EntPayResult depositSmallChange() throws WxPayException {
        EntPayResult entPayResult = WxPayV3Util.depositSmallChange("o-M6O4mMzhvBycpxtu1nXem2Rqh0", "星辰", 30, wxPayProperties);
        return entPayResult;
    }

}
