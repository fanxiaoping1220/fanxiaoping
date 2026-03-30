package com.langchain4j2.controller;

import com.alibaba.dashscope.aigc.imagegeneration.ImageGeneration;
import com.alibaba.dashscope.aigc.imagegeneration.ImageGenerationMessage;
import com.alibaba.dashscope.aigc.imagegeneration.ImageGenerationParam;
import com.alibaba.dashscope.aigc.imagegeneration.ImageGenerationResult;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.langchain4j2.factory.LLMFactory;
import com.langchain4j2.factory.service.LLMService;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 方式二的方式，通过引入langchain4j-community-dashscope模块
 * 原生模式，专用SDK模式，更灵活的方式
 */
@RestController
@RequestMapping("/planBChat")
@RequiredArgsConstructor
public class PlanBChatController {

    private final QwenChatModel qwenChatModel;

    /**
     * 调用千问进行对话
     * @param message 用户输入的消息
     * @return 千问返回的消息
     */
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return qwenChatModel.chat(message);
    }

    /**
     * 简单模式
     * 调用万相进行图片生成
     * @param prompt
     * @return
     */
    @GetMapping("/createImage")
    public String createImage(@RequestParam("prompt") String prompt){
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .apiKey(System.getenv("QWEN_API_KEY"))
                .modelName("wan2.2-t2i-plus")
                .build();
        Response<Image> generate = wanxImageModel.generate(prompt);
        return generate.content().url().toString();
    }

    /**
     * 高级模式
     * 调用万相进行图片生成
     * @param prompt
     * @return
     * @throws NoApiKeyException
     * @throws UploadFileException
     */
    @GetMapping("/createImage2")
    public ImageGenerationResult createImage2(@RequestParam("prompt") String prompt) throws NoApiKeyException, UploadFileException {
        ImageGeneration imageGeneration = new ImageGeneration();
        ImageGenerationMessage message = ImageGenerationMessage.builder()
                .role("user")
                .content(List.of(Map.of("text",prompt)))
                .build();
        ImageGenerationParam param = ImageGenerationParam.builder()
                .apiKey(System.getenv("QWEN_API_KEY"))
                .model(ImageGeneration.Models.WanX2_6_T2I)
                .n(3)
                .size("1280*1280")
                .negativePrompt("")//反向提示词，用于描述不希望在图像中出现的内容，对画面进行限制。
                .promptExtend(true)//是否开启提示词智能改写。开启后，将使用大模型优化正向提示词，对较短的提示词有明显提升效果，但增加3-4秒耗时。
                .watermark(false)//是否显示水印。
                .message(message)
                .build();
        return imageGeneration.call(param);
    }

    /**
     * 工厂模式调用千问进行对话
     * @param message
     * @return
     */
    @GetMapping("/factoryChat")
    public String factoryChat(@RequestParam("message") String message){
        LLMService llmService = LLMFactory.createLLMService(LLMFactory.ModelType.OPEN_AI_DEEPSEEK);
        return llmService.chat(message);
    }
}
