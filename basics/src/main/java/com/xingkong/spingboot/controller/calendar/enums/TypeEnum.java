package com.xingkong.spingboot.controller.calendar.enums;

/**
 * @ClassName: TypeEnum
 * @Description:
 * @Auther: fanxiaoping
 * @Date: 2019/10/20 10:23
 * @version: 1.0.0
 */
public enum TypeEnum {

    /**
     * 节假日
     */
    HOLIDAY_DAY(1,"节假日"),

    /**
     * 休息日
     */
    REST_DAY(2,"休息日"),

    /**
     * 工作日
     */
    WORK_DAY(3,"工作日");


    private Integer code;
    private String name;

    TypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
