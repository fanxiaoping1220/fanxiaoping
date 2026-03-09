package com.langchain4j.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "openAiChatModel",streamingChatModel = "openAiStreamingChatModel")
public interface ConsultantStreamService {

    /**
     * 使用 AI 智能体通义千问模型进行对话
     * 流式响应
     * @param message 对话内容
     * @return
     */
    Flux<String> chat(String message);
}
