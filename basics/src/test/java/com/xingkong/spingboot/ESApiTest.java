package com.xingkong.spingboot;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * * @className: ESApiTest
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/12/7 0007 16:04
 **/
@SpringBootTest
public class ESApiTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static String INDEX = "user";

    @Test
    public void createIndex() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getHits().getTotalHits().value);
        for (SearchHit hit : search.getHits().getHits()) {
            System.out.println(hit.getSourceAsString());
        }
    }
}
