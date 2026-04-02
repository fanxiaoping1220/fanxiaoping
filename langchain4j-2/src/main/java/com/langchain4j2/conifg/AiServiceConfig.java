package com.langchain4j2.conifg;

import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI service配置类
 */
@Configuration
public class AiServiceConfig {

    /**
     * 配置聊天记忆
     */
    @Bean
    public ChatMemory chatMemory(){
        return MessageWindowChatMemory.builder().maxMessages(20).build();
    }

    /**
     * 配置Redis聊天记忆存储
     * @return
     */
    @Bean
    public RedisChatMemoryStore redisChatMemoryStore(){
        return RedisChatMemoryStore.builder().host("1.94.101.207").port(6379).user("default").password("PGFmy8pbBxeJYpYZ").prefix("chat:userMessage:").build();
    }

    /**
     * 配置聊天记忆提供者
     * @return
     */
    @Bean
    public ChatMemoryProvider chatMemoryProvider(RedisChatMemoryStore redisChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder().chatMemoryStore(redisChatMemoryStore).id(memoryId).maxMessages(20).build();
    }
}