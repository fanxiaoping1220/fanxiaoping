package com.langchain4j2.service;

import reactor.core.publisher.Flux;

public interface AiChatIntentionService {

    /**
     * 失物招领-流式输出
     * @param userId
     * @param message
     * @return
     */
    Flux<String> chatStream(Integer userId, String message);
}
