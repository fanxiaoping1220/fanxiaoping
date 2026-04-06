package com.langchain4j2.service.impl;

import com.langchain4j2.aiService.AiAssistant;
import com.langchain4j2.aiService.AiInterionAssistant;
import com.langchain4j2.entity.IntentionOutput;
import com.langchain4j2.entity.LostRegister;
import com.langchain4j2.entity.LostRegisterOutput;
import com.langchain4j2.enums.IntentionEnum;
import com.langchain4j2.service.AiChatIntentionService;
import com.langchain4j2.service.LostRegisterService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class AiChatIntentionServiceImpl implements AiChatIntentionService {

    private final AiAssistant aiAssistant;
    private final AiInterionAssistant aiInterionAssistant;
    private final LostRegisterService lostRegisterService;

    // 用于跟踪用户登记状态
    private final Map<Integer, LostRegistrationSession> registrationSessions = new HashMap<>();

    // 定义确认完成的关键字
    private static final Pattern CONFIRMATION_PATTERNS = Pattern.compile(
            "完成|好了|确认|是的|可以了|ok|okay|yes|是",
            Pattern.CASE_INSENSITIVE
    );

    // 登记会话状态类
    @Data
    private static class LostRegistrationSession {
        private LostRegisterOutput pendingData;
        private boolean awaitingConfirmation;

        public LostRegistrationSession(LostRegisterOutput data) {
            this.pendingData = data;
            this.awaitingConfirmation = false;
        }
    }

    @Override
    public Flux<String> chatStream(Integer userId, String message) {
        IntentionOutput intentionOutput = aiInterionAssistant.intention(userId, message);
        log.info("intentionOutput:{}", intentionOutput);
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
        // 检查是否正在等待用户确认
        LostRegistrationSession session = registrationSessions.get(userId);

        if (session != null && session.isAwaitingConfirmation()) {
            // 检查用户回复是否为确认完成
            boolean isConfirmed = CONFIRMATION_PATTERNS.matcher(message.trim()).find();

            if (isConfirmed) {
                // 用户确认完成，保存数据
                LostRegister lostRegister = new LostRegister();
                BeanUtils.copyProperties(session.getPendingData(), lostRegister);
                lostRegisterService.insert(lostRegister);

                // 清除会话
                registrationSessions.remove(userId);

                return session.getPendingData().getOutput() + "\n\n您的失物信息已成功登记！";
            } else {
                LostRegisterOutput lostRegisterOutput = aiAssistant.registerLost(userId, message);
                LostRegistrationSession newSession = new LostRegistrationSession(lostRegisterOutput);
                newSession.setAwaitingConfirmation(true);
                registrationSessions.put(userId, newSession);
                // 用户未确认，继续等待
                return lostRegisterOutput.getOutput()
                        + "\n\n📋请您确认是否已完成登记：\n\n" +
                        "• 如需补充信息，请直接提供补充内容\n" +
                        "• 如已完成登记，请回复确认关键词（完成/好了/确认/是的/可以了）";
            }
        } else {
            // 正常流程，获取AI助手的输出
            LostRegisterOutput lostRegisterOutput = aiAssistant.registerLost(userId, message);
            log.info("lostRegisterOutput:{}", lostRegisterOutput);

            // 检查AI是否认为已完成，但改为等待用户确认
            if (lostRegisterOutput.getCompleted()) {
                // 不立即保存，而是进入等待确认状态
                LostRegistrationSession newSession = new LostRegistrationSession(lostRegisterOutput);
                newSession.setAwaitingConfirmation(true);
                registrationSessions.put(userId, newSession);

                // 返回提示让用户确认
                return lostRegisterOutput.getOutput() +
                        "\n\n📋 请确认您的失物信息已全部提供完毕：\n\n" +
                        "• 如需补充信息，请直接提供\n" +
                        "• 如已完成登记，请回复确认关键词（完成/好了/确认/是的/可以了）";
            } else {
                // 未完成，直接返回AI的输出
                return lostRegisterOutput.getOutput();
            }
        }
    }
}