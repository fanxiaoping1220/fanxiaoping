package com.xingkong.spingboot.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * * @className: User
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/10/31 0031 17:37
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User {

    private String name;

    private Integer age;

    private String sex;
}
