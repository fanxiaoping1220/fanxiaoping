package com.langchain4j2.service.impl;

import com.langchain4j2.aiService.AiAssistant;
import com.langchain4j2.entity.IntentionOutput;
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

    @Override
    public Flux<String> chatStream(Integer userId, String message) {
        IntentionOutput intentionOutput = aiAssistant.streamIntention(userId, message);
        log.info("intentionOutput:{}", intentionOutput);
        return switch (intentionOutput.getIntention()) {
            case IntentionEnum.ONE ->
                    //丢失信息登记
                    Flux.just(intentionOutput.getOutput());
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
}
