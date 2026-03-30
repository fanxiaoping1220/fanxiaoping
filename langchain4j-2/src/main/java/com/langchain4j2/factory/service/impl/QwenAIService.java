package com.langchain4j2.factory.service.impl;

import com.langchain4j2.factory.service.LLMService;
import dev.langchain4j.community.model.dashscope.QwenChatModel;

public class QwenAIService implements LLMService {

    private final QwenChatModel chatModel;

    public QwenAIService() {
        this.chatModel = QwenChatModel.builder()
                .apiKey(System.getenv("QWEN_API_KEY"))
                .modelName("qwen-plus")
                .build();
    }

    @Override
    public String chat(String prompt) {
        return chatModel.chat(prompt);
    }
}
