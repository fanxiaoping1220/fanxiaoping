package com.langchain4j.demo;

import dev.langchain4j.model.openai.OpenAiChatModel;

public class demo {

    public static void main(String[] args) {
        // 创建模型
        System.out.println(System.getenv("QWEN_API_KEY"));
        OpenAiChatModel mode = OpenAiChatModel.builder()
                .baseUrl("https://dashscope.aliyuncs.com/compatible-mode/v1")
                .apiKey(System.getenv("QWEN_API_KEY"))
                .modelName("qwen-plus")
                .logRequests(true)
                .logResponses(true)
                .build();
        // 调用模型
        String result = mode.chat("HTTP请求方式有哪些，请一一例举，并逐个说明");
        System.out.println(result);
    }
}
