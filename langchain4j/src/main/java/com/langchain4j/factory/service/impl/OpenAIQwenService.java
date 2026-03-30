package com.langchain4j.factory.service.impl;

import com.langchain4j.factory.service.LLMService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAIQwenService implements LLMService {

    private final OpenAiChatModel chatModel;

    public OpenAIQwenService() {
        this.chatModel = OpenAiChatModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey(System.getenv("QWEN_API_KEY"))
                .modelName("qwen-plus")
                .build();
    }

    @Override
    public String chat(String prompt) {
        return chatModel.chat(prompt);
    }
}
