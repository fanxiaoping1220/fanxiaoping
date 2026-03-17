package com.springai.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.spec.DashScopeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * LLM 配置类
 */
@Configuration
public class LLMConfig {

    /**
     * deepseek model
     * @return
     */
    @Bean("deepseekModel")
    public DashScopeChatModel deepseekModel(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("QWEN_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().model(DashScopeModel.ChatModel.DEEPSEEK_V3.value).build())
                .build();
    }

    /**
     * qwen model
     * @return
     */
    @Bean("qwenModel")
    public DashScopeChatModel qwenModel(){
        return DashScopeChatModel.builder()
                .dashScopeApi(DashScopeApi.builder().apiKey(System.getenv("QWEN_API_KEY")).build())
                .defaultOptions(DashScopeChatOptions.builder().model(DashScopeModel.ChatModel.QWEN3_MAX.value).build())
                .build();
    }
}
