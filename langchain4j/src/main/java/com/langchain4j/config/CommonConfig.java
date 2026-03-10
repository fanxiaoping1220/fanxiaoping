package com.langchain4j.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CommonConfig {

//    @Autowired
//    private OpenAiChatModel model;
    private final ChatMemoryStore redisChatMemoryStore;

//    @Bean
//    public ConsultantService consultantService() {
//        return AiServices.builder(ConsultantService.class)
//                .chatModel(model)
//                .build();
//    }

    /**
     * 配置会话记忆
     * @return
     */
    @Bean
    public ChatMemory chatMemory(){
        return MessageWindowChatMemory.builder().maxMessages(20).build();
    }

    @Bean
    public ChatMemoryProvider chatMemoryProvider(){
        return new ChatMemoryProvider(){
            @Override
            public ChatMemory get(Object memoryId) {
                return MessageWindowChatMemory.builder().id(memoryId).maxMessages(20).chatMemoryStore(redisChatMemoryStore).build();
            }
        };
    }

}
