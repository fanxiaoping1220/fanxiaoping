package com.springai.controller;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring ai alibaba dashscope
 */
@RequestMapping("/alibaba/dashscope")
@RestController
@RequiredArgsConstructor
public class DashScopeController {

    private final DashScopeChatModel dashScopeChatModel;

    /**
     * 聊天
     * @param message
     * @return
     */
    @GetMapping(value = "/chat")
    public String chat(@RequestParam("message") String message){
        return dashScopeChatModel.call(message);
    }
}
