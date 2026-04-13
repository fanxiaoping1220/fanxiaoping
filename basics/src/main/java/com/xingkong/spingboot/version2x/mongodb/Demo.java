package com.xingkong.spingboot.version2x.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * * @className: Demo
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/20 11:38
 **/
@Document
@Data
public class Demo implements Serializable {

    @Id
    private Long id;

    @Field(value = "user_name")
    private String userName;

    private String note;

    private List<Role> roles;

}
