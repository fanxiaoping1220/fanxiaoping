package com.springai.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

/**
 * Ai service
 * qwenChatModel
 * 自己实现的ChatModel逻辑
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamChatModel"
)
public interface QwenAiService {

    /**
     * 聊天
     * @param message
     * @return
     */
    String chat(String message);

    /**
     * 流式聊天
     * @param message
     * @return
     */
    Flux<String> streamChat(String message);
}
