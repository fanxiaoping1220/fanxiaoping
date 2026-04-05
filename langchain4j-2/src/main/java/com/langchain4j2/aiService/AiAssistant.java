package com.langchain4j2.aiService;

import com.langchain4j2.entity.IntentionOutput;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel",
        chatMemory = "assistantChatMemory",
        chatMemoryProvider = "assistantChatMemoryProvider",
        tools = {"testTool"}
)
public interface AiAssistant {

    /**
     * 调用千问进行对话
     * @param memoryId
     * @param message
     * @return
     */
    String chat(@MemoryId String memoryId, @UserMessage String message);

    /**
     * 调用千问进行流式对话
     * @param memoryId
     * @param message
     * @return
     */
    Flux<String> streamChat(@MemoryId String memoryId, @UserMessage String message);

    /**
     * 调用千问进行流式意图分析
     * @param sessionId
     * @param message
     * @return
     */
    @SystemMessage(fromResource = "intention.txt")
    @UserMessage("当前sessionId:{{sessionId}},用户的当前消息:{{message}}")
    IntentionOutput streamIntention(@MemoryId @V("sessionId") Integer sessionId, @V("message") String message);
}
