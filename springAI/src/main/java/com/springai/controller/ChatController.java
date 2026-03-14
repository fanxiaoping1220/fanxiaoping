package com.springai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final DeepSeekChatModel deepSeekChatModel;

    @GetMapping
    public String generate(@RequestParam(value = "message", defaultValue = "你是谁") String message){
        System.out.println("message="+message);
        String response = deepSeekChatModel.call(message);
        System.out.println("response="+response);
        return response;
    }
}
