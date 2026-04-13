//package com.langchain4j2.aiService;
//
//import com.langchain4j2.entity.IntentionOutput;
//import dev.langchain4j.service.MemoryId;
//import dev.langchain4j.service.SystemMessage;
//import dev.langchain4j.service.UserMessage;
//import dev.langchain4j.service.V;
//import dev.langchain4j.service.spring.AiService;
//import dev.langchain4j.service.spring.AiServiceWiringMode;
//
///**
// * 为什么要分成2个配置类，防止上下文混淆？共用一个上下文，
// * 这样上下文都是各自独立的，互不干扰。
// */
//@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
//        chatModel = "qwenChatModel",
//        streamingChatModel = "qwenStreamingChatModel",
//        chatMemory = "assistantChatMemory",
//        chatMemoryProvider = "assistantChatMemoryProvider"
//)
///**
// * 为什么需要把系统提示词放到这里呢？
// * 因为系统提示词是每个服务独有的，所以需要放到这里。如果不放到这里，
// * 那么每次调用都会生成一份提示词，只需要第一次调用的时候生成即可。
// */
//@SystemMessage(fromResource = "intention.txt")
//public interface AiInterionAssistant {
//
//    /**
//     * 调用千问进行流式意图分析
//     * @param sessionId
//     * @param message
//     * @return
//     */
//    @UserMessage("当前sessionId:{{sessionId}},用户的当前消息:{{message}}")
//    IntentionOutput intention(@MemoryId @V("sessionId") Integer sessionId, @V("message") String message);
//
//}
