package com.springai.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * langChain4j LLM 配置类
 */
@Configuration
public class LangChain4jLLMConfig {

    /**
     * qwen chatModel
     * @return
     */
    @Bean("qwenChatModel")
    public ChatModel qwenChatModel(){
        return OpenAiChatModel.builder()
                .apiKey(System.getenv("QWEN_API_KEY"))
                .modelName("qwen3.5-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    /**
     * deepseek chatModel
     * @return
     */
    @Bean("deepseekChatModel")
    public ChatModel deepseekChatModel(){
        return OpenAiChatModel.builder()
                .apiKey("sk-dc5bbf751c8643318d0d83fd92d7cd36")
                .modelName("deepseek-reasoner")
                .baseUrl("https://api.deepseek.com")
                .logResponses(true)
                .logRequests(true)
                .build();
    }
}
