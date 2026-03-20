package com.springai.controller;

import com.springai.aiservice.DeepSeekAiService;
import com.springai.aiservice.QwenAiService;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;


@RequestMapping("/langChain4j/chat")
@RestController
public class LangChain4jChat {

    @Autowired
    @Qualifier("qwenChatModel")
    private ChatModel qwenChatModel;

    @Autowired
    @Qualifier("deepseekChatModel")
    private ChatModel deepseekChatModel;

    @Autowired
    private QwenAiService qwenAiService;

    @Autowired
    private DeepSeekAiService deepSeekAiService;

    @Value("classpath:static/image/img.png")
    private Resource resource;

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

    /**
     * qwen
     * 使用aiService chat
     * @param message
     * @return
     */
    @GetMapping("/qwen/serviceChat")
    public String qwenServiceChat(@RequestParam("message") String message){
        return qwenAiService.chat(message);
    }

    /**
     * deepseek
     * 使用aiService chat
     * @param message
     * @return
     */
    @GetMapping("/deepseek/serviceChat")
    public String deepseekServiceChat(@RequestParam("message") String message){
        return deepSeekAiService.chat(message);
    }

    /**
     * 读取图片+文本聊天
     * @param message
     * @return
     * @throws IOException
     */
    @GetMapping("/readerImageChat")
    public String readerImageChat(@RequestParam("message") String message) throws IOException {
        // 图片转base64
        String base64 = Base64.getEncoder().encodeToString(resource.getContentAsByteArray());
        Image image = Image.builder().base64Data(base64).mimeType("image/png").build();
        UserMessage userMessage = new UserMessage(message, ImageContent.from(image));
        ChatResponse chat = qwenChatModel.chat(userMessage);
        return chat.aiMessage().text();
    }

    /**
     * 上传图片+文本聊天
     * @param message
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadImageChat")
    public String uploadImageChat(@RequestParam("message") String message, @RequestBody MultipartFile file) throws IOException {
        String base64 = Base64.getEncoder().encodeToString(file.getBytes());
        Image image = Image.builder().base64Data(base64).mimeType(file.getContentType()).build();
        UserMessage userMessage = new UserMessage(message, ImageContent.from(image));
        ChatResponse chat = qwenChatModel.chat(userMessage);
        return chat.aiMessage().text();
    }
}
