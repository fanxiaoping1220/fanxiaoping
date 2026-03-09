package com.langchain4j.config;

import com.langchain4j.aiservice.ConsultantService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    private OpenAiChatModel model;

    public CommonConfig(OpenAiChatModel model){
        this.model = model;
    }

    @Bean
    public ConsultantService consultantService() {
        return AiServices.builder(ConsultantService.class)
                .chatModel(model)
                .build();
    }

}
