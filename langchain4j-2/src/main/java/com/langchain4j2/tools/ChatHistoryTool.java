package com.langchain4j2.tools;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.langchain4j2.dao.ChatHistoryDao;
import com.langchain4j2.entity.ChatHistory;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 聊天历史工具类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ChatHistoryTool {

    private final ChatHistoryDao chatHistoryDao;

    @Tool("获取用户聊天历史对话")
    public List<ChatHistory> getChatHistory(@P(value = "会话ID") Integer sessionId){
        return chatHistoryDao.selectList(new LambdaQueryWrapper<>(ChatHistory.class)
                .eq(ChatHistory::getSessionId, sessionId)
                .orderByDesc(ChatHistory::getCreateTime)
                .last("limit 20"));
    }
}