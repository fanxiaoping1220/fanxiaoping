package com.xingkong.spingboot.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.xingkong.spingboot.commonutil.Consts;
import com.xingkong.spingboot.commonutil.FileUtil;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

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
            List<List<String[]>> oneList = methodOne(url);
            System.out.println(oneList);
            List<List<String[]>> twoList = methodTwo(url);
            System.out.println(twoList);
        } else {
            System.out.println("调用失败");
        }
    }

    /**
     * 方法一：通过url下载到本地
     * 读取csv中的数据并存入list中
     * @param url 请求地址
     * @return List<List<String[]>>
     * @throws IOException 抛异常
     */
    private static List<List<String[]>> methodOne(String url) throws IOException {
        //获取当前系统路径
        String rootFolder = (new File("")).getAbsolutePath();
        String downloadUrl = rootFolder + File.separator + "bill"+ File.separator + LocalDate.now().minusDays(1).toString()+".zip";
        FileUtil.downloadNet(url,downloadUrl);
        List<String> unzipPath = FileUtil.unzip(downloadUrl, rootFolder + File.separator + "bill" + File.separator);
        List<List<String[]>> dataList = new ArrayList<>();
        for (String path : unzipPath) {
            FileInputStream fileInputStream = new FileInputStream(path);
            reader(dataList, fileInputStream,null);
        }
        return dataList;
    }

    /**
     * 方法二 通过请求地址address---> 得到InputStream ------>转换成ZipInputStream ------> 对zip里面的数据进去读取并存入list中
     * @param address url
     * @return List<List<String[]>>
     * @throws IOException 抛异常
     */
    private static List<List<String[]>> methodTwo(String address) throws IOException {
        URL url = new URL(address);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        ZipInputStream zip = new ZipInputStream(inputStream, Charset.forName(AlipayConstants.CHARSET_GBK));
        List<List<String[]>> dataList = new ArrayList<>();
        //读取数据
        while (zip.getNextEntry() != null){
            reader(dataList,null,zip);
        }
        return dataList;
    }

    /**
     * 读取数据
     * 通过文件输入流读取 FileInputStream
     * 通过压缩包输入流读取 ZipInputStream
     * @param dataList 存储数据的list
     * @param fileInputStream 文件输入流
     * @param zipInputStream 压缩包输入流
     * @throws IOException 抛异常
     */
    private static void reader(List<List<String[]>> dataList, FileInputStream fileInputStream , ZipInputStream zipInputStream) throws IOException {
        InputStreamReader inputStreamReader = null;
        if(fileInputStream != null){
            inputStreamReader = new InputStreamReader(fileInputStream, AlipayConstants.CHARSET_GBK);
        }
        if(zipInputStream != null){
            inputStreamReader = new InputStreamReader(zipInputStream,AlipayConstants.CHARSET_GBK);
        }
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //行文件中所有的数据
        List<String[]> data = new ArrayList<>();
        //暂时存放每一行的数据
        String rowRecord;
        while ((rowRecord = bufferedReader.readLine()) != null){
            String[] lineList = rowRecord.split("\\,");
            if(lineList.length > 4){
                data.add(lineList);
            }
        }
        data.remove(0);
        dataList.add(data);
    }
}