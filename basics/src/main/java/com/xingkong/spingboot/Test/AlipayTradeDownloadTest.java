package com.xingkong.spingboot.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;

import java.io.IOException;
import java.time.LocalDateTime;

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
        String privateKey = "MIIEugIBADANBgkqhkiG9w0BAQEFAASCBKQwggSgAgEAAoIBAQCrRgs3qn17SgNe" +
                "McMrYF8BK1rAPR2xmRshgLz7RB+FpEvb0BJtVak9ASGIzVrvESwXLWLhTbgr2lXg" +
                "zzwLI1PO4GaO8La3oUYBn+TPMsQYCCZ1pYYS06/fNeqn+1lJpn30iZDbzAF/OOkL" +
                "K3SQPvImFL5K738OUjoA9lltrz5oImH4hkZ+YzrdhAdHNCWRwiyITSEzYglutPSE" +
                "5yErcTAWXQ3xFfeZwNPFjYXopmk0Dujla8wLIWV3OpiIKRXf/g5dgAcxM5faSTOR" +
                "Wph3qaPPkF9wZT/lQ305bQXikmOWSn/axpFaqvAVmPRdiou+cV6NyPXrtj24gHTb" +
                "lb9gJndrAgMBAAECggEAeu+244jzssgobLcpUPxGlS9KX4w+wRiVudGPVIfMXH/W" +
                "l3vdji8bYBdsu6YzLEBWNni0beX8WTXJyKjXf2MnJ+o4k0dCz8Hp3wanFPZqfJl1" +
                "CaL6/o6FwaEQwuyOZWglJp6LpMjlFi2CUSsq6tLTuvPOtzj7KQtqaQBqpmlQUSuY" +
                "Lnq8rXFq/WA3R6sas7DbdkBaQnH7vd4K7jeixqGVKY89+EssWG2aMPWuY0nynwvn" +
                "/6HR5d82nwLvUvJxCfr9OI9ZcVDKjjDVIIZ3zbPDKBgP1UoyB24OKPdPvo/D9co1" +
                "Ez7njZ0SE3mH54AzEJR5KbxAO/TVG9DTWXyjvUCSeQKBgQDSBH7RGms+/JBJYOPc" +
                "BHfXYhhIEA1dTggtNlSKscwZ5VM86O/6FHZ603SxYeeCPZ+HraxCMAjwBdddchqu" +
                "nisI9McwhO47QNjx+7dEDFVT2qQS0/wdYtWbY4s95IVr4y+jrksRfjZjwJpSj4Yo" +
                "F+BK9FPBgJezHwThNWEOZrFu5QKBgQDQxfI6bL8HxrQ1JZCKKqfIaVidt51dzQVO" +
                "PzIZnzho68ZohQKhOFQ+QThFw9H0kVy8KZ28Jy4uSAOYe8Oj8HLyJYljo/WrO1fY" +
                "yOjtLrOi4NGiMOAaGoHVwmnDeeX3iIlE8oIc1wxDfhQzyxa4cdG++ARPMpvLp+Yr" +
                "ZNuMkrWYDwJ/GkB1ma2zuivX3VQ/J0FnuYSJhHh9MyyN2RjxeGTBHnuL7BEx7hCg" +
                "vrmv3Hxgc+dACbWr0poug9I3GXcp3qnmtzfhri7vmHiCpbzRK7Be+hxaZgAZg5LX" +
                "IJVQ0mu+Qb4kNlGTEJy2L8HjiG5oVpqlbrkbvxRT4g/+jCgf+azcgQKBgAHXXRWK" +
                "0/x7kidN14KAcc5gr3lknyyunDdec1CqiNLUEYHmeml1l7ic1qQ23+kqRwkZzNxV" +
                "9CBQXke//6+4dSsRja6lSYtjEJlG3FcLLGZpWS/3AmL4jOCXwFsK/i46tAs5oFAk" +
                "zWTi/cafKeoihtkKUu9n4Nvg9TS4xP7uZE+dAoGAGFHh3VZnTgyBQsuqbOaa0WYG" +
                "ZZKHefsZLk5uNb4QveHcCHp3eWv84xg9Kjk43rzJNerfUvrL4gGqO7pGDl1nmQ3X" +
                "An6h7coNNIJIqvdzjqnpYdDNV7dqcHolFhR11LzhoccdgzrDcLfbdgt5LO7DvKKU" +
                "mbtPZgorLEQgTVycsIs=";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAikQvXL/0LJnXcyfmqvATZdbOBspnhfC9UDjERuloXo58eCqDkOwoCSQ0iVC/Pmginbm70tBPAtKixxE9YYHvIMrW5fPp0u3Akl9d+5tUzovKrAvqOfelV+NBqi7TPAMzW7z745qgiXQfoAhMO5zTe8aFuOQo/1Jjre38VtdX8LZBYjh+kTQ/tG+mbmM5zRgxnIjgEDFTPN7sw85t11QbsLYEDGySHFFyHKKHZgaNcNw8RKnZRayxlrCIhn/cdl1CKez8Hkd3AGtg4FZjnjNfUqgN+3vyy5KfZa90wHk3iZNej19TdQA2VM/be3wIr52UjmAAE8KQXILuuhjkUXol6QIDAQAB";
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do","2019041963935952",privateKey, AlipayConstants.FORMAT_JSON,AlipayConstants.CHARSET_UTF8,publicKey,AlipayConstants.SIGN_TYPE_RSA2);
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "\"bill_type\":\"signcustomer\"," +
                "\"bill_date\":\"2019-04-22\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            String url = response.getBillDownloadUrl();
            FileUtil.downloadNet(url,"C:\\Users\\Public\\Desktop\\"+ LocalDateTime.now().toLocalDate()+".zip");
            FileUtil.unzip("C:\\Users\\Public\\Desktop\\"+ LocalDateTime.now().toLocalDate()+".zip","C:\\Users\\Public\\Desktop\\");
        } else {
            System.out.println("调用失败");
        }
    }
}