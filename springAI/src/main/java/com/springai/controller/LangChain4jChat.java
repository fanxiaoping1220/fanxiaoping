package com.springai.controller;

import dev.langchain4j.model.chat.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/langChain4j/chat")
@RestController
public class LangChain4jChat {

    @Autowired
    @Qualifier("qwenChatModel")
    private ChatModel qwenChatModel;

    @Autowired
    @Qualifier("deepseekChatModel")
    private ChatModel deepseekChatModel;

    /**
     * qwen chat
     * @param message
     * @return
     */
    @GetMapping("/qwen")
    public String qwenChat(@RequestParam("message") String message){
        return qwenChatModel.chat(message);
    }

    /**
     * deepseek chat
     * @param message
     * @return
     */
    @GetMapping("/deepseek")
    public String deepseekChat(@RequestParam("message") String message){
        return deepseekChatModel.chat(message);
    }
}
