package com.langchain4j.aiservice;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,chatModel = "openAiChatModel")
//@AiService
public interface ConsultantService {

    /**
     * 用于聊天方法
     * @param message
     * @return
     */
    String chat(String message);
}
