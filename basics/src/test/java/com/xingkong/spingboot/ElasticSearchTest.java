package com.xingkong.spingboot;

import com.alibaba.fastjson.JSON;
import com.xingkong.spingboot.elasticsearch.entity.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * * @className: ElasticSearchTest
 * * @description: ES测试
 * * @author: fan xiaoping
 * * @date: 2022/11/3 0003 14:40
 **/
@SpringBootTest
public class ElasticSearchTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public static final String INDEX = "user";

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(INDEX);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.toString());
        System.out.println(createIndexResponse.isAcknowledged());
    }

    /**
     * 获取索引信息
     * @throws IOException
     */
    @Test
    public void getIndex() throws IOException {
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(new GetIndexRequest(INDEX), RequestOptions.DEFAULT);
        System.out.println(getIndexResponse.getAliases());
        System.out.println(getIndexResponse.getMappings());
        System.out.println(getIndexResponse.getSettings());
    }

    /**
     * 删除索引信息
     * @throws IOException
     */
    @Test
    public void deleteIndex() throws IOException {
        AcknowledgedResponse ack = restHighLevelClient.indices().delete(new DeleteIndexRequest(INDEX), RequestOptions.DEFAULT);
        System.out.println(ack.isAcknowledged());
    }

    /**
     * 添加document数据
     */
    @Test
    public void createDocument() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX);
        indexRequest.id("1001");
        User user = new User("星空",18,"男");
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.getResult());
    }

    /**
     * 更新document
     */
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(INDEX,"1001");
        updateRequest.doc("sex","女");
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse.getResult());
    }

    /**
     * 查询document
     */
    @Test
    public void searchDocument() throws IOException {
        GetResponse getResponse = restHighLevelClient.get(new GetRequest(INDEX, "1001"), RequestOptions.DEFAULT);
        System.out.println(getResponse.getSourceAsString());
    }

    /**
     * 删除document
     */
    @Test
    public void deleteDocument() throws IOException {
        DeleteResponse deleteResponse = restHighLevelClient.delete(new DeleteRequest(INDEX, "1001"), RequestOptions.DEFAULT);
        System.out.println(deleteResponse.toString());
    }

    /**
     * 批量添加document
     */
    @Test
    public void batchAddDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < 10; i++) {
            bulkRequest.add(new IndexRequest(INDEX).id((100+i)+"").source("sex","男","name","如来佛祖"+i,"age",18+i));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.getTook());
        System.out.println(bulkResponse.getItems());
    }

    /**
     * 批量删除document
     */
    @Test
    public void batchDelDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < 10; i++) {
            bulkRequest.add(new DeleteRequest(INDEX,""+(100+i)));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
        System.out.println(bulkResponse.getTook());
        System.out.println(bulkResponse.getItems());
    }

    /**
     * 通过query查询
     */
    @Test
    public void searchQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        System.out.println(searchResponse.getTook());
        System.out.println(hits.getTotalHits().value);
        List<User> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            System.out.println(hit.getSourceAsString());
            list.add(user);
        }
        list.forEach(System.out ::println);
    }

    /**
     * 通过term查询
     */
    @Test
    public void queryTermDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        searchRequest.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age",19)));
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getTook());
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits().value);
        List<User> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out :: println);
    }

    /**
     * 通过term分页查询
     */
    @Test
    public void queryTermPageDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("sex","男"));
        searchSourceBuilder.from(5);
        searchSourceBuilder.size(5);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getTook());
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits().value);
        List<User> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out :: println);
    }

    /**
     * 通过term并排序sort分页查询
     */
    @Test
    public void querySortPageDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name","如"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(5);
        searchSourceBuilder.sort("age", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getTook());
        System.out.println(search.getHits().getTotalHits().value);
        List<User> list = new ArrayList<>();
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out :: println);
    }

    /**
     * 通过term查看并过滤不显示的字段
     */
    @Test
    public void queryFilterFieldDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("sex.keyword","男"));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10);
        searchSourceBuilder.sort("age",SortOrder.DESC);
        searchSourceBuilder.fetchSource(new String[]{"name","age"},new String[]{});
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getTook());
        System.out.println(search.getHits().getTotalHits().value);
        List<User> list = new ArrayList<>();
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out ::println);
    }

    /**
     * 多条件查询 and
     */
    @Test
    public void multipleConditionsQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("age",22));
        boolQueryBuilder.must(QueryBuilders.termQuery("sex.keyword","男"));
        searchSourceBuilder.query(boolQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getTook());
        System.out.println(searchResponse.getHits().getTotalHits().value);
        List<User> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out ::println);
    }

    /**
     * 多条件 or查询
     */
    @Test
    public void orQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.should(QueryBuilders.termQuery("age",23));
        builder.should(QueryBuilders.termQuery("age",22));
        searchSourceBuilder.query(builder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getHits().getTotalHits().value);
        System.out.println(search.getTook());
        List<User> list = new ArrayList<>();
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out :: println);
    }

    /**
     * 范围查询
     */
    @Test
    public void rangeQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(QueryBuilders.termQuery("sex.keyword","男"));
        builder.filter(QueryBuilders.rangeQuery("age").gte(18).lte(22));
        searchSourceBuilder.query(builder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.getHits().getTotalHits().value);
        System.out.println(searchResponse.getTook());
        List<User> list = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits()) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out :: println);
    }

    /**
     * 模糊查询
     */
    @Test
    public void likeQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("name","如来").fuzziness(Fuzziness.ONE));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getHits().getTotalHits().value);
        System.out.println(search.getTook());
        List<User> list = new ArrayList<>();
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out :: println);
    }

    /**
     * 高亮查询
     */
    @Test
    public void highLightQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("sex","男"));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("sex");
        highlightBuilder.preTags("<font style='red'>");
        highlightBuilder.postTags("</font>");
        searchSourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getTook());
        System.out.println(search.getHits().getTotalHits().value);
        List<User> list = new ArrayList<>();
        for (SearchHit hit : search.getHits()) {
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            System.out.println(hit.getSourceAsString());
            list.add(user);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("sex");
            StringBuilder sex = new StringBuilder();
            for (Text text : highlightField.getFragments()) {
                sex.append(text);
            }
            user.setSex(sex.toString());
        }
        list.stream().forEach(System.out :: println);
    }

    /**
     * 聚合查询 最大值 max
     */
    @Test
    public void aggregationQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.aggregation(AggregationBuilders.max("max_age").field("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getTook());
        System.out.println(search.getHits().getTotalHits().value);
        Map<String,Object> result = new HashMap<>();
        List<User> list = new ArrayList<>();
        System.out.println(search.getHits().toString());
        System.out.println(search.getHits());
        for (SearchHit hit : search.getHits()) {
            System.out.println(hit.getSourceAsString());
            User user = JSON.parseObject(hit.getSourceAsString(), User.class);
            list.add(user);
        }
        list.stream().forEach(System.out :: println);
        result.put("list",list);
        System.out.println(search.getAggregations());
        System.out.println(search.getAggregations().toString());
        Max max = search.getAggregations().get("max_age");
        result.put("max_avg",max.value());
        System.out.println(result.toString());
    }

    /**
     * 聚合查询 分组查询
     */
    @Test
    public void aggregationGroupQueryDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest(INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.terms("group_age").field("age"));
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(search.getTook());
        System.out.println(search.getHits().getTotalHits().value);
        Aggregations aggregations = search.getAggregations();
        Terms terms = aggregations.get("group_age");
        System.out.println("====================");
        Map<String,Object> result = new HashMap<>();
        terms.getBuckets().stream().forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
        });
        result.put("group_age",terms.getBuckets());
        System.out.println(result.toString());
    }
}
