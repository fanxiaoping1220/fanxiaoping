package com.langchain4j.controller;

import com.langchain4j.aiservice.ConsultantService;
import com.langchain4j.aiservice.ConsultantStreamService;
import dev.langchain4j.model.openai.OpenAiChatModel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiChatModel model;
    private final ConsultantService consultantService;
    private final ConsultantStreamService consultantStreamService;

    /**
     * 使用 AI 智能体通义千问模型进行对话
     *
     * @param message 对话内容
     * @return
     */
    @GetMapping
    public String chat(@RequestParam (value = "message") String message) {
        return model.chat(message);
    }

    /**
     * 使用 AI service 进行对话
     *
     * @param message 对话内容
     * @return
     */
    @GetMapping(value = "/aiServiceChat")
    public String aiServiceChat(@RequestParam(value = "message") String message) {
        return consultantService.chat(message);
    }

    /**
     * 使用 AI service 进行流式对话
     * @param message
     * @return
     */
    @GetMapping(value = "/streamChat",produces = "text/html;charset=UTF-8")
    public Flux<String> streamChat(@RequestParam(value = "message") String message){
        return consultantStreamService.chat(message);
    }
}
