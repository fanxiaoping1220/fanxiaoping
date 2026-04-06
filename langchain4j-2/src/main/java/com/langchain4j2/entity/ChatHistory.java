package com.langchain4j2.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天记录(ChatHistory)表实体类
 *
 * @author makejava
 * @since 2026-04-06 19:47:55
 */
@TableName("chat_history")
@Data
public class ChatHistory {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    //会话id
    private Integer sessionId;
    //角色
    private String role;
    //聊天内容
    private String content;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;

}

