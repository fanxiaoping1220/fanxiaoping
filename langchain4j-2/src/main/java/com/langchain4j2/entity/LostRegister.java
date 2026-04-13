package com.langchain4j2.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 失物登记表(LostRegister)实体类
 *
 * @author makejava
 * @since 2026-04-06 15:10:40
 */
@Data
@TableName("lost_register")
public class LostRegister implements Serializable {
    private static final long serialVersionUID = 580329828448571092L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 姓名
     */
    private String userName;
    /**
     * 失物名称
     */
    private String lostName;
    /**
     * 失物特证
     */
    private String lostType;
    /**
     * 手机号
     */
    private String phone;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}

