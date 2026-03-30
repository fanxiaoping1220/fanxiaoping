package com.langchain4j2.factory.service.impl;

import com.langchain4j2.factory.service.LLMService;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class OpenAIDeepSeekService implements LLMService {

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
