package com.langchain4j2.aiService;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@dev.langchain4j.service.spring.AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        streamingChatModel = "openAiStreamingChatModel",
        chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider"
)
public interface AiService {

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
    Flux<String> chatStream(String message);

    /**
     * 流式聊天
     * @param memoryId The ID of the chat memory.
     * @param userMessage The user message.
     * @return
     */
    @UserMessage("你是我的好朋友，请用日语回答我的问题。今天是{{current_date}} {{message}})")
//    @SystemMessage(value = "你是我的好朋友，请用英语话回答我的问题。今天是 {{current_date}}")
//    @SystemMessage(fromResource = "system_message.txt")
    Flux<String> chatStream(@MemoryId String memoryId, @V("message") String userMessage);
//    Flux<String> chatStream(@MemoryId String memoryId,@UserMessage String userMessage);
}
