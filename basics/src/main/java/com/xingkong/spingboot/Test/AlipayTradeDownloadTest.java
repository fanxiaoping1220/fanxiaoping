package com.xingkong.spingboot.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.xingkong.spingboot.commonutil.Consts;
import com.xingkong.spingboot.commonutil.FileUtil;

import java.io.IOException;
import java.time.LocalDate;

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
    public static void main(String[] args) throws AlipayApiException, IOException { 
        AlipayClient alipayClient = new DefaultAlipayClient(Consts.URL,Consts.APP_ID,Consts.PRIVATE_KEY, AlipayConstants.FORMAT_JSON,AlipayConstants.CHARSET_UTF8,Consts.ALIPAY_PUBLIC_KEY,AlipayConstants.SIGN_TYPE_RSA2);
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "\"bill_type\":\"signcustomer\"," +
                "\"bill_date\":\""+ LocalDate.now().minusDays(1).toString() +"\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            String url = response.getBillDownloadUrl();
            FileUtil.downloadNet(url,Consts.FILE_PATH);
            FileUtil.unzip(Consts.FILE_PATH,Consts.FILE_UNZIP_PATH);
        } else {
            System.out.println("调用失败");
        }
    }
}