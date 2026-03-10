package com.langchain4j.aiservice;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,//显式注入手动转配
        chatModel = "openAiChatModel",//模型
        streamingChatModel = "openAiStreamingChatModel",
        chatMemory = "chatMemory",//会话记忆
        chatMemoryProvider = "chatMemoryProvider"//会话记忆提供者
    )
public interface ConsultantStreamService {

    /**
     * 使用 AI 智能体通义千问模型进行对话
     * 流式响应
     * @param message 对话内容
     * @return
     */
    //指定系统消息
//    @SystemMessage("你是小平的助手小爱，人美又多金")
    @SystemMessage(fromResource = "system-message.txt")
//    @UserMessage(value = "你是小平的助手小爱，人美又多金!{{it}}")
//    @UserMessage(value = "你是小平的助手小爱，人美又多金!{{msg}}")
//    Flux<String> chat(@V("msg") String message);
    Flux<String> chat(@MemoryId String memoryId, @UserMessage String message);
}
