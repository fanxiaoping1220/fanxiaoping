package com.xingkong.spingboot.service.bill.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.xingkong.spingboot.commonutil.Consts;
import com.xingkong.spingboot.controller.bill.dto.AlipayBillDetailDTO;
import com.xingkong.spingboot.producer.AlipayBillProducer;
import com.xingkong.spingboot.service.bill.dao.AlipayBillDetailDAO;
import com.xingkong.spingboot.service.bill.dao.AlipayBillTotalDAO;
import com.xingkong.spingboot.service.bill.entity.AlipayBillDetailDO;
import com.xingkong.spingboot.service.bill.entity.AlipayBillTotalDO;
import com.xingkong.spingboot.service.bill.service.AlipayBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @className: AlipayBillServiceIMpl
 * @description:
 * @author: 范小平
 * @date: 2019-09-10 10:57
 * @version: 1.0.0
 */
@Service
public class AlipayBillServiceImpl implements AlipayBillService {

    @Autowired
    private AlipayBillDetailDAO alipayBillDetailDAO;

    @Autowired
    private AlipayBillTotalDAO alipayBillTotalDAO;

    @Autowired
    private AlipayBillProducer alipayBillProducer;

    @Override
    public String getYesterdayBill() throws AlipayApiException, IOException{
        AlipayClient alipayClient = new DefaultAlipayClient(Consts.URL,Consts.APP_ID,Consts.PRIVATE_KEY, AlipayConstants.FORMAT_JSON,AlipayConstants.CHARSET_UTF8,Consts.ALIPAY_PUBLIC_KEY,AlipayConstants.SIGN_TYPE_RSA2);
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "\"bill_type\":\"signcustomer\"," +
                "\"bill_date\":\""+ LocalDate.now().minusDays(4).toString() +"\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
            String url = response.getBillDownloadUrl();
            //获取数据
            List<List<String[]>> data = methodTwo(url);
            //存储数据
            //方案一同步处理数据
//            saveData(data);
            //方案二异步处理采用队列存储数据
            alipayBillProducer.sendAlipayBill(data);
            return "success";
        } else {
            System.out.println("调用失败");
        }
        return "fail";
    }

    @Override
    public JSONArray getList(AlipayBillDetailDTO alipayBillDetailDTO) {
        LocalDateTime startTime = LocalDateTime.now().minusDays(alipayBillDetailDTO.getNumber()).with(LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.now().minusDays(alipayBillDetailDTO.getNumber()).with(LocalTime.MAX);
        if(alipayBillDetailDTO.getNumber() > 1){
            alipayBillDetailDTO.setNumber(1);
            endTime = LocalDateTime.now().minusDays(alipayBillDetailDTO.getNumber()).with(LocalTime.MAX);
        }
        List<AlipayBillDetailDO> list = alipayBillDetailDAO.getList(startTime, endTime, alipayBillDetailDTO.getOrderRole());
        return JSONArray.parseArray(JSON.toJSONString(list));
    }

    /**
     * 方法二 通过请求地址address---> 得到InputStream ------>转换成ZipInputStream ------> 对zip里面的数据进去读取并存入list中
     * @param address url
     * @return List<List<String[]>>
     * @throws IOException 抛异常
     */
    private List<List<String[]>> methodTwo(String address) throws IOException {
        List<List<String[]>> dataList = new ArrayList<>();
        URL url = new URL(address);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        ZipInputStream zipInputStream = new ZipInputStream(inputStream,Charset.forName(AlipayConstants.CHARSET_GBK));
        //读取数据
        while(zipInputStream.getNextEntry() != null){
            reader(dataList,zipInputStream);
        }
        return dataList;
    }

    /**
     * 读取数据
     * 通过压缩包输入流读取 ZipInputStream
     * @param dataList 存储数据的list
     * @param zipInputStream 压缩包输入流
     * @throws IOException 抛异常
     */
    private void reader(List<List<String[]>> dataList, ZipInputStream zipInputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(zipInputStream,Charset.forName(AlipayConstants.CHARSET_GBK));
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

    /**
     * 保存数据到数据库
     * 支付宝账单明细表
     * 支付宝账单汇总表
     * @param dataList
     */
    public void saveData(List<List<String[]>> dataList){
        for (List<String[]> data : dataList) {
            if(data.get(0).length > 6 || data.get(1).length > 6){
                //存入支付宝账单明细表
                List<AlipayBillDetailDO> billDetailList = new ArrayList<>();
                for (String[] o : data) {
                    AlipayBillDetailDO billDetailDO = new AlipayBillDetailDO();
                    billDetailDO.setBillSerialNumber(o[0].trim());
                    billDetailDO.setBusinessSerialNumber(o[1].trim());
                    billDetailDO.setGoodsOrderNumber(o[2].trim());
                    billDetailDO.setGoodsName(o[3].trim());
                    LocalDateTime localDateTime = LocalDateTime.parse(o[4].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    billDetailDO.setCreateTime(localDateTime);
                    billDetailDO.setPayAccountNumber(o[5].trim());
                    billDetailDO.setInMoney(BigDecimal.valueOf(Double.valueOf(o[6].trim())));
                    billDetailDO.setOutMoney(BigDecimal.valueOf(Double.valueOf(o[7].trim())));
                    billDetailDO.setAccountBalanceMoney(BigDecimal.valueOf(Double.valueOf(o[8].trim())));
                    billDetailDO.setDealChannel(o[9].trim());
                    billDetailDO.setBusinessType(o[10].trim());
                    billDetailDO.setRemark(o[11].trim());
                    billDetailList.add(billDetailDO);
                }
                //批量插入
                alipayBillDetailDAO.branchInsert(billDetailList);
            }else{
                //存入支付宝账单汇总表
                List<AlipayBillTotalDO> billTotalList = new ArrayList<>();
                for (String[] o : data) {
                    AlipayBillTotalDO billTotalDO = new AlipayBillTotalDO();
                    billTotalDO.setType(o[0].trim());
                    billTotalDO.setInNumber(Integer.valueOf(o[1].trim()));
                    billTotalDO.setInMoney(BigDecimal.valueOf(Double.valueOf(o[2].trim())));
                    billTotalDO.setOutNumber(Integer.valueOf(o[3].trim()));
                    billTotalDO.setOutMoney(BigDecimal.valueOf(Double.valueOf(o[4].trim())));
                    billTotalDO.setTotalMoney(BigDecimal.valueOf(Double.valueOf(o[5].trim())));
                    billTotalDO.setCreateTime( LocalDateTime.now().minusDays(1));
                    billTotalList.add(billTotalDO);
                }
                //批量插入
                alipayBillTotalDAO.brnanchInsert(billTotalList);
            }
        }
    }
}