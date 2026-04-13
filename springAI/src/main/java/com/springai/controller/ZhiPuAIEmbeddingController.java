package com.springai.controller;

import com.springai.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.zhipuai.ZhiPuAiEmbeddingModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 智普ai
 * Embedding model
 */
@RestController
@RequestMapping("/ai/zhipu")
@RequiredArgsConstructor
@Slf4j
public class ZhiPuAIEmbeddingController {

    private final ZhiPuAiEmbeddingModel embeddingModel;
    private final EmbeddingService embeddingService;

    /**
     * 对用户传入的文本进行向量化处理，测试embedding
     * @param message
     * @return
     */
    @GetMapping(value = "/embedding")
    public Map<String,Object> embedding(@RequestParam(value = "message", defaultValue = "你是谁") String message){
        log.info("message: {}", message);
        float[] embed = embeddingModel.embed(message);
        return Map.of("embedding", embed, "message", message);
    }

    /**
     * 对用户传入的文本进行相似度计算，得到最相似的文本
     * @param message
     * @return
     */
    @GetMapping(value = "/similarity")
    public String similarity(@RequestParam(value = "message") String message){
        return embeddingService.queryBestMatch(message);
    }
}
