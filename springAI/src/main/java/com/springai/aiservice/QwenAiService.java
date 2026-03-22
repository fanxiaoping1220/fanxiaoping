package com.springai.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
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
        streamingChatModel = "qwenStreamChatModel",
        chatMemory = "langChainchatMemory",
        chatMemoryProvider = "chatMemoryProvider"
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


    /**
     * 流式聊天
     * @param memoryId 会话id
     * @param message 消息
     * @return
     */
    Flux<String> streamChatMemory(@MemoryId String memoryId, @UserMessage String message);
}
