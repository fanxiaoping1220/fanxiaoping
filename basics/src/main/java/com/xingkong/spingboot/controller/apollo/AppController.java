package com.xingkong.spingboot.controller.apollo;

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
public class AppController {

    @Autowired
    private ApolloAppDAO apolloAppDAO;

    /**
     * 获取列表
     *
     * @return
     */
    @GetMapping(value = "/getList")
    JSONArray getList() {
        List<ApolloApp> list = apolloAppDAO.getList();
        return JSONArray.parseArray(JSON.toJSONString(list));
    }
}