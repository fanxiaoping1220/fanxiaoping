package com.langchain4j2.controller;

import com.alibaba.dashscope.aigc.imagegeneration.ImageGeneration;
import com.alibaba.dashscope.aigc.imagegeneration.ImageGenerationMessage;
import com.alibaba.dashscope.aigc.imagegeneration.ImageGenerationParam;
import com.alibaba.dashscope.aigc.imagegeneration.ImageGenerationResult;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.langchain4j2.aiService.AiService;
import com.langchain4j2.entity.LegalQuestion;
import com.langchain4j2.entity.Person;
import com.langchain4j2.enums.RateTierEnum;
import com.langchain4j2.factory.LLMFactory;
import com.langchain4j2.factory.service.LLMService;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.output.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 方式二的方式，通过引入langchain4j-community-dashscope模块
 * 原生模式，专用SDK模式，更灵活的方式
 */
@Slf4j
@RestController
@RequestMapping("/planBChat")
@RequiredArgsConstructor
public class PlanBChatController {

    private final QwenChatModel qwenChatModel;
    private final AiService aiService;

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
     * aiService方式聊天
     * @param message
     * @return
     */
    @GetMapping("/serviceChat")
    public String serviceChat(@RequestParam("message") String message){
        return aiService.chat(message);
    }

    /**
     * aiService方式聊天流式
     * @param message
     * @return
     */
    @GetMapping("/serviceStreamChat")
    public Flux<String> serviceStreamChat(@RequestParam("message") String message){
        return aiService.chatStream(message);
    }

    /**
     * aiService方式聊天流式
     * @param memoryId 内存id
     * @param message
     * @return
     */
    @GetMapping("/serviceStreamChat/{memoryId}")
    public Flux<String> serviceStreamChat(@PathVariable String memoryId, @RequestParam("message") String message){
        return aiService.chatStream(memoryId, message);
    }

    /**
     * 带参数-aiService方式聊天流式
     * @param memoryId
     * @param message
     * @param name
     * @param age
     * @return
     */
    @GetMapping("/serviceStreamChat2")
    public Flux<String> serviceStreamChat2(@RequestParam("memoryId") String memoryId, @RequestParam("message") String message,
                                           @RequestParam("name") String name,@RequestParam("age") Integer age){
        return aiService.chatStream(memoryId, message,name,age);
    }

    /**
    * 从文本中提取整数
     * @param text
     * @return
     */
    @GetMapping("/extractInteger")
    public Integer extractInteger(@RequestParam("text") String text){
        return aiService.extractInteger(text);
    }

    /**
     * 从文本中提取长整型
     * @param text
     * @return
     */
    @GetMapping("/extractLong")
    public Long extractLong (@RequestParam("text") String text){
        return aiService.extractLong(text);
    }

    /**
     * 从文本中提取人物信息
     * @param text
     * @return
     */
    @GetMapping("/extractPerson")
    public Person extractPerson(@RequestParam("text") String text){
        return aiService.extractPerson(text);
    }

    /**
     * 从文本中提取布尔值
     * @param text
     * @return
     */
    @GetMapping("/extractBoolean")
    public Boolean extractBoolean(@RequestParam("text") String text){
        return aiService.extractBoolean(text);
    }

    /**
     * 从文本中提取评价等级
     * @param text
     * @return
     */
    @GetMapping("/extractRateTier")
    public RateTierEnum extractRateTier(@RequestParam("text") String text) {
        return aiService.extractRateTier(text);
    }

    /**
     * 根据法律规定回答问题
     * @param legalQuestion
     * @return
     */
    @PostMapping("/answerLegalQuestion")
    public Flux<String> answerLegalQuestion(@RequestBody LegalQuestion legalQuestion){
        return aiService.answerLegalQuestion(legalQuestion);
    }

    /**
     * 计算流式聊天
     * @param memoryId
     * @param userMessage
     * @return
     */
    @GetMapping("/calculateStream")
    public Flux<String> calculateStream(@RequestParam("memoryId") String memoryId, @RequestParam("userMessage") String userMessage){
        return aiService.calculateStream(memoryId, userMessage);
    }

    /**
     * 商品聊天流式
     * @param memoryId
     * @param message
     * @return
     */
    @GetMapping("/productChatStream")
    public Flux<String> productChatStream(@RequestParam("memoryId") String memoryId, @RequestParam("message") String message){
        return aiService.productChatStream(memoryId, message);
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
        LLMService llmService = LLMFactory.createLLMService(LLMFactory.ModelType.DASHSCOPE_QWEN);
        return llmService.chat(message);
    }

    /**
     * 创建订单接口
     * @return
     */
    @GetMapping("/createOrder")
    public ResponseEntity<Map<String,Object>> createOrder(){
        System.out.println("开始创建订单..."+ LocalDateTime.now());
        System.out.println("创建订单成功..."+ LocalDateTime.now());
        Map<String,Object> response = Map.of("code",200,"size",0,"message","创建订单成功");
        return ResponseEntity.ok(response);
    }

    /**
     * 同步订单接口
     * @return
     */
    @GetMapping("/syncOrder")
    public ResponseEntity<String> syncOrder(@RequestParam ("size") Integer size){
        System.out.println("开始同步订单..."+ LocalDateTime.now());
        log.info("size:{}",size);
        System.out.println("同步订单成功..."+ LocalDateTime.now());
        return ResponseEntity.ok("同步订单成功");
    }
}
