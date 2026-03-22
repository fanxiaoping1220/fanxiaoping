package com.springai.config;

import dev.langchain4j.community.store.embedding.redis.RedisEmbeddingStore;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.community.store.memory.chat.redis.StoreType;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * langChain4j LLM 配置类
 */
@Configuration
public class LangChain4jLLMConfig {

    @Autowired
    private ChatMemoryStore redisChatMemoryStore;

    /**
     * qwen chatModel
     *
     * @return
     */
    @Bean("qwenChatModel")
    public ChatModel qwenChatModel() {
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
     *
     * @return
     */
    @Bean("deepseekChatModel")
    public ChatModel deepseekChatModel() {
        return OpenAiChatModel.builder()
                .apiKey("sk-dc5bbf751c8643318d0d83fd92d7cd36")
                .modelName("deepseek-reasoner")
                .baseUrl("https://api.deepseek.com")
                .logResponses(true)
                .logRequests(true)
                .build();
    }

    /**
     * qwen streaming chatModel
     * @return
     */
    @Bean("qwenStreamChatModel")
    public StreamingChatModel qwenStreamChatModel() {
        return OpenAiStreamingChatModel.builder()
                .apiKey(System.getenv("QWEN_API_KEY"))
                .modelName("qwen3.5-plus")
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .logRequests(true)
                .logResponses(true)
                .build();
    }

    /**
     * deepseek streaming chatModel
     * @return
     */
    @Bean("deepseekStreamChatModel")
    public StreamingChatModel deepseekStreamChatModel(){
        return OpenAiStreamingChatModel.builder()
                .apiKey("sk-dc5bbf751c8643318d0d83fd92d7cd36")
                .modelName("deepseek-reasoner")
                .baseUrl("https://api.deepseek.com")
                .logResponses(true)
                .logRequests(true)
                .build();
    }

    @Bean("langChainchatMemory")
    public ChatMemory langChainchatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(20).build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider() {
        return new ChatMemoryProvider() {
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder().id(memoryId).maxMessages(20).chatMemoryStore(redisChatMemoryStore).build();
            }
        };
    }

}
