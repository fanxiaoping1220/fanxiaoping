package com.langchain4j2.entity;

import com.langchain4j2.enums.IntentionEnum;
import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

/**
 * 意图输出
 */
@Data
public class IntentionOutput {

    /**
     * 意图类型
     */
    @Description({"意图分析，分析意图的类型","ONE:代表的是丢失信息登记","TWO:代表的是找到失物登记","THREE:代表的是失物查询","OTHER:代表的是其他意图"})
    private IntentionEnum intention;

    /**
     * 意图输出
     */
    @Description("大模型对用户的输出")
    private String output;
}
