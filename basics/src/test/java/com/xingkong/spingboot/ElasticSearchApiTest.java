package com.xingkong.spingboot;

import com.alibaba.fastjson.JSON;
import com.xingkong.spingboot.elasticsearch.entity.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

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
        User user = new User("星空", 19);
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
}
