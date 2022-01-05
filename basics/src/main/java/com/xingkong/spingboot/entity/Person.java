package com.xingkong.spingboot.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * * @className: Person
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/1/5 0005 16:26
 **/
@Data
@Component
@ConfigurationProperties(prefix = "person")
public class Person {

    private Long id;

    private Integer age;

    private Date birthday;

    private Map<String,String> map;

    private List<String> list;
}
