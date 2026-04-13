package com.langchain4j2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.langchain4j2.dao.ChatHistoryDao;
import com.langchain4j2.entity.ChatHistory;
import com.langchain4j2.service.ChatHistoryService;
import org.springframework.stereotype.Service;

/**
 * 聊天记录(ChatHistory)表服务实现类
 *
 * @author makejava
 * @since 2026-04-06 19:47:55
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryDao, ChatHistory> implements ChatHistoryService {

}

