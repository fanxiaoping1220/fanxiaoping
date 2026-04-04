package com.langchain4j2.entity;

import dev.langchain4j.model.output.structured.Description;
import lombok.Data;

@Data
public class Person {

    @Description("姓名")
    private String name;

    @Description("年龄")
    private int age;

    @Description("地址")
    private String address;

    @Description("手机号")
    private String phone;

    @Description("邮箱")
    private String email;
}
