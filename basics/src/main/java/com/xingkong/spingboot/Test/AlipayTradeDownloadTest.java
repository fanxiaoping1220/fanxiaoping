package com.xingkong.spingboot.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;

/**
 * @className: AlipayTradeDownloadTest
 * @description: 支付宝查询对账单下载测试
 * @author: 范小平
 * @date: 2019-04-19 10:35
 * @version: 1.0.0
 */
public class AlipayTradeDownloadTest {

    /**
     *  String serverUrl; https请求地址
     *  String appId;
     *  String privateKey; 秘钥
     *  String format; josn
     *  String charset; 编码格式
     *  String alipayPublicKey; 支付宝公钥
     *  String signType; 签名类型
     * @param args
     * @throws AlipayApiException
     */
    public static void main(String[] args) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","app_id","your private_key", AlipayConstants.FORMAT_JSON,AlipayConstants.CHARSET_UTF8,"alipay_public_key",AlipayConstants.SIGN_TYPE_RSA2);
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "\"bill_type\":\"trade\"," +
                "\"bill_date\":\"2019-04-05\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
    }

}