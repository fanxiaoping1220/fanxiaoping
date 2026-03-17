package com.springai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.springai.service.RagService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * spring ai alibaba dashscope
 */
@RequestMapping("/alibaba/dashscope")
@RestController
public class DashScopeController {

    @Autowired
    @Qualifier("qwenModel")
    private  DashScopeChatModel qwenChatModel;

    @Autowired
    @Qualifier("deepseekModel")
    private DashScopeChatModel deepseekChatModel;

    @Autowired
    @Qualifier("qwenChatClient")
    private ChatClient qwenChatClient;

    @Autowired
    private RagService ragService;

    /**
     * qwen聊天
     * @param message
     * @return
     */
    @GetMapping(value = "/qwenChat")
    public String qwenChat(@RequestParam("message") String message){
        return qwenChatModel.call(message);
    }

    /**
     * qwen聊天流式返回
     * @param message
     * @return
     */
    @GetMapping(value = "/qwenStreamChat")
    public Flux<String> qwenStreamChat(@RequestParam("message") String message){
        return qwenChatModel.stream(message);
    }

    /**
     * deepseek聊天
     * @param message
     * @return
     */
    @GetMapping(value = "/deepseekChat")
    public String deepseekChat(@RequestParam("message") String message){
        return deepseekChatModel.call(message);
    }

    /**
     * deepseek聊天流式返回
     * @param message
     * @return
     */
    @GetMapping(value = "/deepseekStreamChat")
    public Flux<String> deepseekStreamChat(@RequestParam("message") String message){
        return deepseekChatModel.stream(message);
    }

    /**
     * qwen chat client 聊天
     * @param message
     * @return
     */
    @GetMapping(value = "/chatClient")
    public String chatClient(@RequestParam("message") String message){
        return qwenChatClient.prompt().user(message).call().content();
    }

    /**
     * qwen chat client 聊天流式返回
     * @param message
     * @return
     */
    @GetMapping(value = "/streamChatClient")
    public Flux<String> StreamChatClient(@RequestParam("message") String message){
        return qwenChatClient.prompt()
                .user(message)
                .stream()
                .content();
    }

    /**
     * 使用提示词的方式聊天
     * qwen chat client 聊天流式返回
     * @param message
     * @return
     */
    @GetMapping(value = "/streamPromptChat")
    public Flux<String> streamPromptChat(@RequestParam("message") String message){
        return qwenChatClient.prompt()
                .system("你是一个法律组手，只能回答法律相关的问题，其他问题一概不回答")
                .user(message)
                .stream()
                .content();
    }

    /**
     * 使用知识库的方式回答用户问题
     * @param message
     * @return
     */
    @GetMapping(value = "/streamAnswer")
    public Flux<String> streamAnswer(@RequestParam ("message") String message){
        return ragService.streamAnswer(message);
    }
}
