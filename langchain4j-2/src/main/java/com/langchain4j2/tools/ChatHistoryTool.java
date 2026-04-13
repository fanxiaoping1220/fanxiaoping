package com.langchain4j2.tools;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.langchain4j2.dao.ChatHistoryDao;
import com.langchain4j2.dao.LostRegisterDao;
import com.langchain4j2.entity.ChatHistory;
import com.langchain4j2.entity.LostRegister;
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
    private final LostRegisterDao lostRegisterDao;

    @Tool("获取用户聊天历史对话")
    public List<ChatHistory> getChatHistory(@P(value = "会话ID") Integer sessionId){
        return chatHistoryDao.selectList(new LambdaQueryWrapper<ChatHistory>()
                .eq(ChatHistory::getSessionId, sessionId)
                .orderByDesc(ChatHistory::getCreateTime)
                .last("limit 20"));
    }

    @Tool({"根据手机号获取失物招领信息","获取用户手机号对应的失物登记信息"})
    public List<LostRegister> getLostRegisterByPhone(@P(value = "手机号") String phone){
        return lostRegisterDao.selectList(new LambdaQueryWrapper<LostRegister>()
                .eq(LostRegister::getPhone, phone)
                .orderByDesc(LostRegister::getUpdateTime));
    }
}