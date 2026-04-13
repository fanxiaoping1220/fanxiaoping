package com.langchain4j2.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 意图枚举
 */
@Getter
@AllArgsConstructor
public enum IntentionEnum {

    ONE("ONE","代表的是丢失信息登记"),
    TWO("TWO","代表的是找到失物登记"),
    THREE("THREE","代表的是失物查询"),
    OTHER("OTHER","代表的是其他意图");

    private final String code;
    private final String desc;
}
