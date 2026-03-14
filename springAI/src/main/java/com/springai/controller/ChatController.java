package com.springai.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final DeepSeekChatModel deepSeekChatModel;

    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping
    public String generate(@RequestParam(value = "message", defaultValue = "你是谁") String message){
        System.out.println("message="+message);
        String response = deepSeekChatModel.call(message);
        System.out.println("response="+response);
        return response;
    }

    /**
     * 流式聊天
     * @param message
     * @return
     */
    @GetMapping(value = "/generateStream")
    public Flux<String> generateStream(@RequestParam(value = "message", defaultValue = "你是谁") String message,
                                       HttpServletResponse httpServletResponse){
        //设置响应体编码为UTF-8
        httpServletResponse.setCharacterEncoding("UTF-8");
        System.out.println("message="+message);
        Flux<String> response = deepSeekChatModel.stream(message);
        System.out.println("response="+response);
        return response;
    }
}
