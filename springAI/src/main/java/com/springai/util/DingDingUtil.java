package com.springai.util;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalkworkflow_1_0.models.QueryIntegratedTodoTaskHeaders;
import com.aliyun.dingtalkworkflow_1_0.models.QueryIntegratedTodoTaskRequest;
import com.aliyun.dingtalkworkflow_1_0.models.QueryIntegratedTodoTaskResponse;
import com.aliyun.dingtalkworkflow_1_0.models.QueryIntegratedTodoTaskResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DingDingUtil {

    private static Config config () throws Exception {
        Config config = new Config();
        config.protocol = "https";
        config.regionId = "central";
        return config;
    }

    private static com.aliyun.dingtalkoauth2_1_0.Client authClient()throws Exception{
        return new com.aliyun.dingtalkoauth2_1_0.Client(config());
    }

    private static com.aliyun.dingtalkworkflow_1_0.Client workflowClient()throws Exception{
        return new com.aliyun.dingtalkworkflow_1_0.Client(config());
    }

    /**
     * 获取钉钉的accessToken
     * @return
     * @throws Exception
     */
    public static String getAccessToken() throws Exception {
        com.aliyun.dingtalkoauth2_1_0.Client client = authClient();
        GetAccessTokenRequest accessTokenRequest = new GetAccessTokenRequest()
                .setAppKey("dingoqxsulucatbmihkt")
                .setAppSecret("S6ur0Wr6NRtAXwlU2wRmgAcJKiHyYvarpRbo3zsrwQfeNIKCEom-JXYMxsaQrmAE");
        GetAccessTokenResponse accessToken = client.getAccessToken(accessTokenRequest);
        return accessToken.getBody().getAccessToken();

    }

    public static List<QueryIntegratedTodoTaskResponseBody.QueryIntegratedTodoTaskResponseBodyResultList> queryIntegratedTodoTask(String userId) throws Exception {
        com.aliyun.dingtalkworkflow_1_0.Client client = workflowClient();
        QueryIntegratedTodoTaskHeaders headers = new QueryIntegratedTodoTaskHeaders();
        headers.xAcsDingtalkAccessToken = getAccessToken();
        QueryIntegratedTodoTaskRequest request = new QueryIntegratedTodoTaskRequest()
                .setUserId(userId)
                .setPageSize(10)
                .setPageNumber(1);
        QueryIntegratedTodoTaskResponse response = client.queryIntegratedTodoTaskWithOptions(request, headers, new RuntimeOptions());
        return response.getBody().getResult().getList();
    }

    public static void main(String[] args) throws Exception {
        List<QueryIntegratedTodoTaskResponseBody.QueryIntegratedTodoTaskResponseBodyResultList> list = queryIntegratedTodoTask("1234");
        System.out.println(list);
    }



}
