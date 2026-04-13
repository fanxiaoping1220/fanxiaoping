package com.xingkong.spingboot.echarts;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * * @className: Test
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/1/13 0013 15:05
 **/
@RequestMapping(value = "/echartsTest")
@RestController
public class EchartsTest {

    @GetMapping(value = "/test3")
    public String test3(){
        //制造数据
        List<String> aoxList = new ArrayList<>();
        aoxList.add("2");  aoxList.add("4");  aoxList.add("7");aoxList.add("9");  aoxList.add("10");  aoxList.add("9");
        aoxList.add("8");  aoxList.add("8");  aoxList.add("7");
        List<String> aoList = new ArrayList<>();
        aoList.add("1");  aoList.add("4");  aoList.add("7");aoList.add("9");  aoList.add("10");  aoList.add("10");
        aoList.add("10");  aoList.add("10");  aoList.add("10");
        // 模板参数
        HashMap<String, Object> datas = new HashMap<>();
        //aohour、aoList、aoxList对应模板/template/option.ftl中的x和y轴的名字,模板可自行修改，
        //ftl 为设置模板的名字。路径在/templates下
        datas.put("title", JSON.toJSONString("wxw图示"));
        datas.put("colordata", JSON.toJSONString("#000000"));
        datas.put("aoxList", JSON.toJSONString(aoxList));
        datas.put("aoList", JSON.toJSONString(aoList));
        datas.put("ftl", "template/option.ftl");
        datas.put("folder","/template");
        try {
            System.out.println( EchartsUtil.getImage(datas));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}