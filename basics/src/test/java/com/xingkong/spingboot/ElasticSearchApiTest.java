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
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * * @className: ElasticSearchApiTest
 * * @description: ES Api 测试
 * * @author: fan xiaoping
 * * @date: 2022/10/31 0031 11:04
 **/
@SpringBootTest
public class ElasticSearchApiTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final String INDEX = "xing_index";

    /**
     * 测试 索引的创建 request put xing_index
     * @throws IOException
     */
    @Test
    public void  TestCreateIndex() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(INDEX);
        //2.客户端执行请求 IndicesClient, 请求后获得相应
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    /**
     * 测试获取索引,判断其是否存在
     */
    @Test
    public void getIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(INDEX);
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 测试删除索引
     */
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX);
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    /**
     * 测试创建文档
     */
    @Test
    public void createDocument() throws IOException {
        //1.创建对象
        User user = new User("星空", 18);
        //2.创建请求
        IndexRequest request = new IndexRequest(INDEX);
        request.id("1");
        request.timeout("1s");
        //3.将数据放入请求的json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //4.客户端发送请求,获得相应结果
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    /**
     * 测试获取文档 是否存在  get/index/_doc/1
     */
    @Test
    public void getDocumentExists() throws IOException {
        GetRequest request = new GetRequest(INDEX, "1");
        //不获取返回_source的上下文
        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        request.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    /**
     * 测试获取文档信息
     */
    @Test
    public void getDocument() throws IOException {
        GetRequest request = new GetRequest(INDEX,"1");
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        //打印文档的内容
        System.out.println(getResponse.getSourceAsString());
        System.out.println(getResponse);
    }

    /**
     * 测试更新文档信息
     * @throws IOException
     */
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest(INDEX,"1");
        User user = new User("星辰说未来", 22);
        request.timeout("1s");
        request.doc(JSON.toJSONString(user),XContentType.JSON);
        UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(updateResponse.status());
    }

    /**
     * 测试删除文档
     */
    @Test
    public void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX, "1");
        request.timeout("1s");
        DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(deleteResponse.status());
    }

    /**
     * 测试批量插入文档
     */
    @Test
    public void batchInsertDocument() throws IOException {
        BulkRequest request = new BulkRequest();
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new User("星辰说未来",i));
        }
        //批量处理
        for (int i = 0; i < list.size(); i++) {
            //批量更新和批量删除就在这里修改对应的请求就可以了
            request.add(new IndexRequest(INDEX).id(""+(i+1)).source(JSON.toJSONString(list.get(i)),XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        //是否失败 false没有
        System.out.println(bulkResponse.hasFailures());
    }

    /**
     * 搜索文档
     * 1.SearchRequest 创建搜索请求
     * 2.SearchSourceBuilder 构建查询条件
     * HighlightBuilder 构建高亮
     * QueryBuilders.termQuery 精确查询
     * QueryBuilders.matchAllQuery() 查询所有
     * 3.searchRequest.source 将查询条件放到source里面
     * 4.restHighLevelClient.search 获取查询结果
     * @throws IOException
     */
    @Test
    public void searchDocument() throws IOException {
        //1.搜索的index
        SearchRequest searchRequest = new SearchRequest(INDEX);
        //2.构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //QueryBuilders.termQuery 精确查询
        //QueryBuilders.matchAllQuery() 查询所有
        //string 默认type为test 需要添加keyword
        searchSourceBuilder.query(QueryBuilders.termQuery("name.keyword","星辰说未来"));
        searchSourceBuilder.timeout(TimeValue.timeValueMillis(60));
        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);
        //3.把搜索条件放到查询的source里面
        searchRequest.source(searchSourceBuilder);
        //4.查询获取查询结果
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }
        List<User> list = new ArrayList<>();
        Arrays.stream(searchResponse.getHits().getHits()).forEach(documentFields -> {
            User user = JSON.parseObject(documentFields.getSourceAsString(), User.class);
            list.add(user);
        });
        System.out.println(list.size());
    }
}
