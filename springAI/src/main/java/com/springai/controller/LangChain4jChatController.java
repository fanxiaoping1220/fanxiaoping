package com.springai.controller;

import com.springai.aiservice.DeepSeekAiService;
import com.springai.aiservice.QwenAiService;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.IOException;
import java.util.Base64;
import java.util.function.Consumer;


@RequestMapping("/langChain4j/chat")
@RestController
public class LangChain4jChatController {

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

    @Autowired
    @Qualifier("qwenStreamChatModel")
    private StreamingChatModel qwenStreamChatModel;

    @Autowired
    @Qualifier("deepseekStreamChatModel")
    private StreamingChatModel deepseekStreamChatModel;

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

    /**
     * qwen 通过streamChatModel 进行流式聊天
     * qwen streaming chat
     * @param message
     * @return
     */
    @GetMapping("/qwen/streamChat")
    public Flux<String> qwenStreamChat(@RequestParam("message") String message){
        return Flux.create(new Consumer<FluxSink<String>>() {
            @Override
            public void accept(FluxSink<String> stringFluxSink) {
                qwenStreamChatModel.chat(message, new StreamingChatResponseHandler() {

                    @Override
                    public void onPartialResponse(String partialResponse){
                        stringFluxSink.next(partialResponse);
                    }


                    @Override
                    public void onCompleteResponse(ChatResponse completeResponse) {
                        stringFluxSink.complete();
                    }

                    @Override
                    public void onError(Throwable error) {
                        stringFluxSink.error(error);
                    }
                });
            }
        });
    }

    /**
     * deepseek 通过streamChatModel 进行流式聊天
     * deepseek streaming chat
     * @param message
     * @return
     */
    @GetMapping("/deepseek/streamChat")
    public Flux<String> deepseekStreamChat(@RequestParam("message") String message){
        return Flux.create(stringFluxSink -> deepseekStreamChatModel.chat(message, new StreamingChatResponseHandler() {

            @Override
            public void onPartialResponse(String partialResponse) {
                stringFluxSink.next(partialResponse);
            }

            @Override
            public void onCompleteResponse(ChatResponse completeResponse) {
                stringFluxSink.complete();

            }

            @Override
            public void onError(Throwable error) {
                stringFluxSink.error(error);
            }
        }));
    }

    /**
     * qwen
     * 使用aiService 进行流式聊天
     * @param message
     * @return
     */
    @GetMapping("/aiService/qwen/streamChat")
    public Flux<String> aiServiceQwenStreamChat(@RequestParam("message") String message){
        return qwenAiService.streamChat(message);
    }

    /**
     * deepseek
     * 使用aiService 进行流式聊天
     * @param message
     * @return
     */
    @GetMapping("/aiService/deepseek/streamChat")
    public Flux<String> aiServiceDeepSeekStreamChat(@RequestParam("message") String message){
        return deepSeekAiService.streamChat(message);
    }
}
