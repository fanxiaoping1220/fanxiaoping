package com.langchain4j2.controller;

import com.langchain4j2.aiService.AiAssistant;
import com.langchain4j2.service.AiChatIntentionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * AI智能助手接口
 */
@RequestMapping("/ai")
@RestController
@RequiredArgsConstructor
public class AssistantController {

    private final AiAssistant aiAssistant;
    private final AiChatIntentionService aiChatIntentionService;;

    /**
     * 聊天
     * @param memoryId
     * @param message
     * @return
     */
    @GetMapping("/chat")
    public String chat(@RequestParam ("memoryId") String memoryId, @RequestParam ("message") String message){
        return aiAssistant.chat(memoryId, message);
    }

    /**
     * 流式聊天
     * @param memoryId
     * @param message
     * @return
     */
    @GetMapping("/streamChat")
    public Flux<String> streamChat(@RequestParam ("memoryId") String memoryId, @RequestParam ("message") String message){
        return aiAssistant.streamChat(memoryId, message);
    }

    /**
     * 流式意图分析
     * @param sessionId 用户id
     * @param message 用户消息
     * @return
     */
    @GetMapping("/streamIntention")
    public Flux<String> streamIntention(@RequestParam ("sessionId") Integer sessionId,
                                                 @RequestParam ("message") String message){
        return Flux.just(aiChatIntentionService.chatStream(sessionId, message));
    }
}
