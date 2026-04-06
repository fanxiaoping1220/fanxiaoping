package com.langchain4j2.aspect;

import com.langchain4j2.entity.ChatHistory;
import com.langchain4j2.service.ChatHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;

/**
 * 聊天流程切面
 * 主要用于保存会话聊天记录
 */
@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatFlowAop {

    private final ChatHistoryService chatHistoryService;

    // 用户角色标识
    public static final String USER_ROLE = "USER";
    // AI角色标识
    public static final String AI_ROLE = "AI";

    @Pointcut("@annotation(com.langchain4j2.annotation.ChatFlow)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Integer sessionId = (Integer) args[0];
        String message = (String) args[1];
        log.info("sessionId:{}, message:{}",sessionId,message);
        //保存用户聊天记录
        saveChatHistory(sessionId, message, USER_ROLE);
        Object result = joinPoint.proceed();
        log.info("result:{}",result);
        //保存AI聊天记录
        saveChatHistory(sessionId, result.toString(), AI_ROLE);
        return result;
    }

    /**
     * 保存聊天记录
     * @param sessionId
     * @param message
     * @param userRole
     */
    private void saveChatHistory(Integer sessionId, String message, String userRole) {
        ChatHistory chatHistory = new ChatHistory();
        chatHistory.setSessionId(sessionId);
        chatHistory.setRole(userRole);
        chatHistory.setContent(message);
        chatHistoryService.save(chatHistory);
    }
}
