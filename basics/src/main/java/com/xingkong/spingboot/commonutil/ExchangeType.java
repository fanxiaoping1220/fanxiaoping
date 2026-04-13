package com.xingkong.spingboot.commonutil;

/**
 * @ClassName ExchangeType
 * @Description 交换器类型
 * @Author fanxiaoping
 * @Date 2018/9/26 16:34
 * @Version 1.0.0
 **/
public enum ExchangeType {

    /**
     * type = direct
     */
    EXCHANGE_TYPE_DIRECT("direct"),

    /**
     * type = fanout
     */
    EXCHANGE_TYPE_FANOUT("fanout"),

    /**
     * type = topic
     */
    EXCHANGE_TYPE_TOPIC("topic"),

    /**
     * type = header
     */
    EXCHANGE_TYPE_HEADER("header");

    private String name;

    ExchangeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
