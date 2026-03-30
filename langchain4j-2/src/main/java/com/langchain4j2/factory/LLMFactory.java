package com.langchain4j2.factory;


import com.langchain4j2.factory.service.LLMService;
import com.langchain4j2.factory.service.impl.OpenAIDeepSeekService;
import com.langchain4j2.factory.service.impl.OpenAIQwenService;
import com.langchain4j2.factory.service.impl.QwenAIService;

/**
 * LLM工厂类
 * 模型工厂类
 */
public class LLMFactory {

    public enum ModelType{
        OPEN_AI_DEEPSEEK,
        OPEN_AI_QWEN,
        DASH_SCOPE_QWEN
    }

    public static LLMService createLLMService(ModelType modelType) {
        switch (modelType) {
            case OPEN_AI_DEEPSEEK -> {
                return new OpenAIDeepSeekService();
            }
            case OPEN_AI_QWEN -> {
                return new OpenAIQwenService();
            }
            case DASH_SCOPE_QWEN -> {
                return new QwenAIService();
            }
            default -> {
                throw new IllegalArgumentException("不支持的模型类型");
            }
        }
    }
}
