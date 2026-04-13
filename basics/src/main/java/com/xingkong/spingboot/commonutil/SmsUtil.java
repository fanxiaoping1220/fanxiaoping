package com.xingkong.spingboot.commonutil;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * * @className: SmsUtil
 * * @description: 添加创蓝智能云平台--短信
 * * @author: fan xiaoping
 * * @date: 2023/2/6 0006 11:55
 **/
public class SmsUtil {

    public static void main(String[] args) {
        //短信下发
        String sendUrl = "https://smssh1.253.com/msg/v1/send/json";
        Map map = new HashMap();
        map.put("account","N6460121");//API账号
        map.put("password","MUQnRjWx1q5fad");//API密码
        String spec = "彩基镀锌0.35*1000 100吨";
        map.put("msg","【人堆】您好，有买家报"+spec+"请查看");//短信内容
        map.put("phone","18324485857,15839178732");//手机号
        map.put("report","true");//是否需要状态报告
        map.put("extend","123");//自定义扩展码
        JSONObject js = (JSONObject) JSONObject.toJSON(map);
        System.out.println(sendSmsByPost(sendUrl,js.toString()));
        //查询余额
        String balanceUrl = "https://smssh1.253.com/msg/balance/json";
        Map map1 = new HashMap();
        map1.put("account","N6460121");
        map1.put("password","MUQnRjWx1q5fad");
        JSONObject js1 = (JSONObject) JSONObject.toJSON(map1);
        System.out.println(sendSmsByPost(balanceUrl,js1.toString()));
    }

    public static String sendSmsByPost(String path, String postContent) {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os=httpURLConnection.getOutputStream();
            os.write(postContent.getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
