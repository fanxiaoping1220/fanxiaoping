package com.springai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
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
}
