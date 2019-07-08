package com.xingkong.spingboot.Test;

import com.xingkong.spingboot.commonutil.HttpUtil;
import org.springframework.http.HttpStatus;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.nio.charset.Charset;

/**
 * @ClassName Test
 * @Description
 * @Author fanxiaoping
 * @Date 2018/9/21 11:13
 * @Version 1.0.0
 **/
public class Test {

    public static void main(String[] args) {
//        ThreadDome td = new ThreadDome();
//        new  Thread(td).start();
//        while(true){
//            if(td.isFlag()){
//                System.out.println("~~~~~~~~~~~~~~~~~~~~~");
//                break;
//            }
//        }
        String postParam = "<ap>  <head>    <tr_code>330002</tr_code>    <corp_no>8000173493</corp_no>    <user_no>00005</user_no>    <req_no>20190625023047</req_no>    <tr_acdt>20190625</tr_acdt>    <tr_time>055244</tr_time>    <atom_tr_count>1</atom_tr_count>    <channel>0</channel>    <reserved></reserved>  </head>  <body><cert_no>20190625023047</cert_no><pay_acno>331066100018010039226</pay_acno><type>S</type><sum>1</sum><sum_amt>0.10</sum_amt><pay_month>201906</pay_month><summary><summary>意向金转房东--户名:郭毅</summary><busi_no>3310004684S</busi_no><tran><rcd><card_no>6222620170015721157</card_no><acname>郭毅</acname><card_flag>0</card_flag><amt>0.10</amt><busino></busino></rcd></tran></body></ap>";
        HttpUtil.sendPost("http://60.191.72.118:7890",postParam);
        Response response = null;
        try {
            Client client = ClientBuilder.newClient();
            MultivaluedHashMap<String, Object> map = new MultivaluedHashMap<>();
            map.add("content-Type", "application/xml;charset=GB2312");
            WebTarget target = client.target("http://60.191.72.118:7890");
            response = target.request().headers(map).buildPost(Entity.text(postParam)).invoke();
            //返回状态
            if (HttpStatus.OK.value() == response.getStatus()) {
                String data = new String(response.readEntity(String.class).getBytes(), Charset.forName("GB2312"));
                System.out.println(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
}
