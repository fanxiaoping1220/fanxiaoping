package com.langchain4j2.conifg;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Assistant configuration
 */
@Configuration
public class AssistantConfig {

    /**
     * Assistant chat memory配置聊天记忆
     * @return
     */
    @Bean
    public ChatMemory assistantChatMemory(){
        return MessageWindowChatMemory.builder().maxMessages(20).build();
    }

    /**
    *
    *Assistant chat memory provider聊天记忆提供器
     * @return
     */
    @Bean
    public ChatMemoryProvider assistantChatMemoryProvider(){
        return memoryId -> MessageWindowChatMemory.builder().id(memoryId)
                .maxMessages(20).chatMemoryStore(new InMemoryChatMemoryStore()).build();
    }
}
