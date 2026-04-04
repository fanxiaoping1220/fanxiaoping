package com.langchain4j2.enums;

import dev.langchain4j.model.output.structured.Description;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 评价等级枚举
 */
@Getter
@AllArgsConstructor
public enum RateTierEnum {

    @Description("评价非常不满意")
    ONE(1, "非常不满意"),
    @Description("评价不满意")
    TWO(2, "不满意"),
    @Description("评价一般")
    THREE(3, "一般"),
    @Description("评价满意")
    FOUR(4, "满意"),
    @Description("评价非常满意")
    FIVE(5, "非常满意");

    private final int value;
    private final String desc;

}
