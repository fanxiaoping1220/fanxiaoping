package com.langchain4j2.service.impl;

import com.langchain4j2.aiService.AiAssistant;
import com.langchain4j2.annotation.ChatFlow;
import com.langchain4j2.entity.IntentionOutput;
import com.langchain4j2.entity.LostRegister;
import com.langchain4j2.entity.LostRegisterOutput;
import com.langchain4j2.enums.IntentionEnum;
import com.langchain4j2.service.AiChatIntentionService;
import com.langchain4j2.service.LostRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AiChatIntentionServiceImpl implements AiChatIntentionService {

    private final AiAssistant aiAssistant;
    private final LostRegisterService lostRegisterService;

    @ChatFlow
    @Override
    public String chatStream(Integer sessionId, String message) {
        IntentionOutput intentionOutput = aiAssistant.intention(sessionId, message);
        log.info("intentionOutput:{}", intentionOutput);
        return switch (intentionOutput.getIntention()) {
            case IntentionEnum.ONE ->
                //丢失信息登记
                    registerLost(sessionId, message);
            case IntentionEnum.TWO ->
                //找到失物登记
                    intentionOutput.getOutput();
            case IntentionEnum.THREE ->
                //失物查询
                    intentionOutput.getOutput();
            default ->
                //其他
                    intentionOutput.getOutput();
        };
    }

    private String registerLost(Integer userId, String message) {
        // 正常流程，获取AI助手的输出
        LostRegisterOutput lostRegisterOutput = aiAssistant.registerLost(userId, message);
        log.info("lostRegisterOutput:{}", lostRegisterOutput);
        if (lostRegisterOutput.getCompleted()) {
            // 用户确认完成，保存数据
            LostRegister lostRegister = new LostRegister();
            BeanUtils.copyProperties(lostRegisterOutput, lostRegister);
            lostRegisterService.insert(lostRegister);
        }
        return lostRegisterOutput.getOutput();
    }
}