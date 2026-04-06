package com.langchain4j2.service;

public interface AiChatIntentionService {

    /**
     * 失物招领-流式输出
     * @param sessionId
     * @param message
     * @return
     */
    String chatStream(Integer sessionId, String message);
}
