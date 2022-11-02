package com.xingkong.spingboot.elasticsearch.controller;

import com.xingkong.spingboot.elasticsearch.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * * @className: ContentController
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/11/2 0002 10:44
 **/
@RequestMapping(value = "/content")
@RestController
public class ContentController {

    @Autowired
    private ContentService contentService;

    /**
     * 解析keywords数据存入es
     * @param keywords
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/parse/{keywords}")
    public Boolean parse(@PathVariable(name = "keywords") String keywords) throws IOException {
        return contentService.parseContent(keywords);
    }

    /**
     * 分页查询
     * @param keyword
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/search/{keyword}/{pageNo}/{pageSize}")
    public Map<String, Object> search(@PathVariable(name = "keyword") String keyword, @PathVariable(name = "pageNo") Integer pageNo, @PathVariable(name = "pageSize") Integer pageSize) throws IOException {
        if(pageNo == null || pageSize == null){
            pageNo = 0;
            pageSize = 10;
        }
        return contentService.searchPage(keyword,pageNo,pageSize);
    }

    /**
     * 分页高亮查询
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/searchHighLight/{keyword}/{pageNo}/{pageSize}")
    public Map<String,Object> searchPageHighLight(@PathVariable(name = "keyword") String keyword,@PathVariable(name = "pageNo") Integer pageNo,@PathVariable(name = "pageSize") Integer pageSize) throws IOException {
        if(pageNo == null || pageSize == null){
            pageNo = 0;
            pageSize = 10;
        }
        return contentService.searchPageHighLight(keyword,pageNo,pageSize);
    }

}
