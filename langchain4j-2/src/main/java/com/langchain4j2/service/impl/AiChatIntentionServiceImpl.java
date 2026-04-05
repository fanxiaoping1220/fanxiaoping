package com.langchain4j2.service.impl;

import com.langchain4j2.aiService.AiAssistant;
import com.langchain4j2.aiService.AiInterionAssistant;
import com.langchain4j2.entity.IntentionOutput;
import com.langchain4j2.entity.LostRegisterOutput;
import com.langchain4j2.enums.IntentionEnum;
import com.langchain4j2.service.AiChatIntentionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
@Service
public class AiChatIntentionServiceImpl implements AiChatIntentionService {

    private final AiAssistant aiAssistant;
    private final AiInterionAssistant aiInterionAssistant;

    @Override
    public Flux<String> chatStream(Integer userId, String message) {
        IntentionOutput intentionOutput = aiInterionAssistant.intention(userId, message);
        log.info("intentionOutput:{}", intentionOutput);
        String output = intentionOutput.getOutput();
        return switch (intentionOutput.getIntention()) {
            case IntentionEnum.ONE ->
                    //丢失信息登记
                    Flux.just(registerLost(userId, message));
            case IntentionEnum.TWO ->
                    //找到失物登记
                    Flux.just(intentionOutput.getOutput());
            case IntentionEnum.THREE ->
                    //失物查询
                    Flux.just(intentionOutput.getOutput());
            default ->
                    //其他
                    Flux.just(intentionOutput.getOutput());
        };
    }

    private String registerLost(Integer userId, String message) {
        LostRegisterOutput lostRegisterOutput = aiAssistant.registerLost(userId, message);
        log.info("lostRegisterOutput:{}", lostRegisterOutput);
        return lostRegisterOutput.getOutput();
    }
}
