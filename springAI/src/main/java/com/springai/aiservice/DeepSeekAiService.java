package com.springai.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * AI service
 * deepseekChatModel
 * 自己实现的ChatModel逻辑
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "deepseekChatModel")
public interface DeepSeekAiService {

    /**
     * 聊天
     * @param message
     * @return
     */
    String chat(String message);
}
