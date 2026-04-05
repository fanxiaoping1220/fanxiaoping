package com.langchain4j2.aiService;

import com.langchain4j2.entity.LegalQuestion;
import com.langchain4j2.entity.Person;
import com.langchain4j2.enums.RateTierEnum;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

@dev.langchain4j.service.spring.AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "openAiChatModel",
        streamingChatModel = "openAiStreamingChatModel",
        chatMemory = "chatMemory",
        chatMemoryProvider = "chatMemoryProvider",
        tools = "calculateTool"
)
public interface AiService {

    /**
     * 聊天
     * @param message
     * @return
     */
    String chat(String message);

    /**
    * 流式聊天
     * @param message
     * @return
     */
    Flux<String> chatStream(String message);

    /**
     * 流式聊天
     * @param memoryId The ID of the chat memory.
     * @param userMessage The user message.
     * @return
     */
//    @UserMessage("你是我的好朋友，请用日语回答我的问题。今天是{{current_date}} {{message}})")
    @SystemMessage(value = "你是我的好朋友，请用湖北话回答我的问题。今天是 {{current_date}}")
//    @SystemMessage(fromResource = "system_message.txt")
//    Flux<String> chatStream(@MemoryId String memoryId, @V("message") String userMessage);
    Flux<String> chatStream(@MemoryId String memoryId,@UserMessage String userMessage);

    /**
     * 带参数-流式聊天
     * @param memoryId The ID of the chat memory.
     * @param userMessage The user message.
     * @param name 姓名
     * @param age 年龄
     * @return
     */
    @SystemMessage(fromResource = "system_message2.txt")
    Flux<String> chatStream(@MemoryId String memoryId, @UserMessage String userMessage,@V("name") String name,@V("age") Integer age);

    /**
     * 从文本中提取整数
     * @param text
     * @return
     */
    Integer extractInteger(String text);

    /**
     * 从文本中提取长整型
     * @param text
     * @return
     */
    Long extractLong(String text);

    /**
     * 从文本中提取人物信息
     * @param text
     * @return
     */
    Person extractPerson(String text);

    /**
     * 从文本中提取布尔值
     * @param text
     * @return
     */
    @UserMessage("评价{{text}}是否为好评?")
    Boolean extractBoolean(@V("text") String text);

    /**
     * 评价等级
     * 从文本中提取枚举值
     * @param text
     * @return
     */
    RateTierEnum extractRateTier(String text);

    /**
     * 根据法律规定回答问题
     * @param legalQuestion
     * @return
     */
    @SystemMessage("你是一个专业的中国法律专家，只根据法律规定回答问题，" +
            "输出限制：其他领域的问题禁止回答，直接返回，抱歉，我只能回答中国法律相关的的问题")
    Flux<String> answerLegalQuestion(LegalQuestion legalQuestion);

    /**
     * 计算流式聊天
     * @param memoryId
     * @param userMessage
     * @return
     */
    Flux<String> calculateStream(@MemoryId String memoryId, @UserMessage String userMessage);
}
