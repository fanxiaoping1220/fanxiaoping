package com.langchain4j.controller;

import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiChatModel model;

    /**
     * 使用 AI 智能体通义千问模型进行对话
     * @param message 对话内容
     * @return
     */
    @GetMapping
    public String chat(String message){
        return model.chat(message);
    }
}
