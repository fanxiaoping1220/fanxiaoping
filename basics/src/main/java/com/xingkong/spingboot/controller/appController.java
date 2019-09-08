package com.xingkong.spingboot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.xingkong.spingboot.entity.ApolloApp;
import com.xingkong.spingboot.mapper.ApolloAppDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: appController
 * @description: 阿波罗apollo---APP ----- API
 * @author: 范小平
 * @date: 2019-09-08 10:22
 * @version: 1.0.0
 */
@RestController
@RequestMapping(value = "/apollo/app")
public class appController {

    @Autowired
    private static ApolloAppDAO apolloAppDAO;

    @GetMapping(value = "/getList")
    JSONArray getList(){
        List<ApolloApp> list = apolloAppDAO.getList();
        return JSONArray.parseArray(JSON.toJSONString(list));
    }
}