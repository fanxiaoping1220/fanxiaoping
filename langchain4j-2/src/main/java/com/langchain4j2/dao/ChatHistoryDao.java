package com.langchain4j2.dao;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.langchain4j2.entity.ChatHistory;

/**
 * 聊天记录(ChatHistory)表数据库访问层
 *
 * @author makejava
 * @since 2026-04-06 19:47:55
 */
@Mapper
public interface ChatHistoryDao extends BaseMapper<ChatHistory> {

}

