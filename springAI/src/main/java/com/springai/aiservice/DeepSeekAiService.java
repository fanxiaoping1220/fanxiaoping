package com.springai.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

/**
 * AI service
 * deepseekChatModel
 * 自己实现的ChatModel逻辑
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "deepseekChatModel",
        streamingChatModel = "deepseekStreamChatModel"
)
public interface DeepSeekAiService {

    /**
     * 聊天
     * @param message
     * @return
     */
    String chat(String message);

    /**
     * 聊天流式
     * @param message
     * @return
     */
    Flux<String> streamChat(String message);
}
