package com.xingkong.spingboot.elasticsearch.service;

import com.alibaba.fastjson.JSON;
import com.xingkong.spingboot.elasticsearch.entity.Content;
import com.xingkong.spingboot.elasticsearch.util.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * * @className: ContentService
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/11/2 0002 10:31
 **/
@Service
public class ContentService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private HtmlParseUtil htmlParseUtil;

    /**
     * 解析数据放入es
     * @param keywords
     * @return
     */
    public Boolean parseContent(String keywords) throws IOException {
        List<Content> list = htmlParseUtil.parseJD(keywords);
        //把查询到的数据放入es中
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("1m");
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest("jd_goods").source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkResponse.hasFailures();
    }

    /**
     * 分页查询
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Map<String,Object> searchPage(String keyword, Integer pageNo, Integer pageSize) throws IOException {
        Map<String,Object> result = new HashMap<>();
        //查询请求
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        //构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("title",keyword));
        //设置分页
        int from = 0;
        if(pageNo > 1){
            from = (pageNo - 1) * pageSize;
        }
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        //查询得到结果集
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Content> list = new ArrayList<>();
        //将结果集存入list
        Arrays.stream(searchResponse.getHits().getHits()).forEach(documentFields -> {
            Content content = JSON.parseObject(documentFields.getSourceAsString(), Content.class);
            list.add(content);
        });
        long totalPage = searchResponse.getHits().getTotalHits().value / pageSize;
        if(searchResponse.getHits().getTotalHits().value % pageSize > 0){
            totalPage++;
        }
        result.put("currentPage",pageNo);
        result.put("total",searchResponse.getHits().getTotalHits().value);
        result.put("list",list);
        result.put("totalPage",totalPage);
        return result;
    }
}
