package com.langchain4j2.entity;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

/**
 * 失物登记信息
 */
@Data
public class LostRegisterOutput {

    /**
     * 大模型输出的内容
     */
    @Description("大模型输出的内容")
    private String output;

    /**
     * 用户名
     */
    @Description("用户名")
    private String userName;

    /**
     * 手机号
     */
    @Description("手机号")
    private String phone;

    /**
     * 失物名称
     */
    @Description("失物名称")
    private String lostName;

    /**
     * 失物特征
     */
    @Description("失物特征")
    private String lostType;

    /**
     * 是否完成登记
     */
    @Description("是否完成登记")
    private Boolean completed;
}
