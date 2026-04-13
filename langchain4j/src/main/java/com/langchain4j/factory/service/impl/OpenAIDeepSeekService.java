package com.langchain4j.factory.service.impl;

import com.langchain4j.factory.service.LLMService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAIDeepSeekService implements LLMService {

    // 在类加载时设置系统属性，确保早于 SPI 扫描
    static {
        System.setProperty("langchain4j.http.clientBuilderFactory", 
            "dev.langchain4j.http.client.jdk.JdkHttpClientBuilderFactory");
    }

    private final OpenAiChatModel openAiChatModel;

    public OpenAIDeepSeekService() {
        this.openAiChatModel = OpenAiChatModel.builder()
                .apiKey(System.getenv("QWEN_API_KEY"))
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .modelName("deepseek-v3.2")
                .build();
    }

    @Override
    public String chat(String prompt) {
        return openAiChatModel.chat(prompt);
    }
}
