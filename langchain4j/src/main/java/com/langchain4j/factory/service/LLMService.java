package com.langchain4j.factory.service;

public interface LLMService {

    /**
     * 聊天
     * @param prompt
     * @return
     */
    String chat(String prompt);
}
