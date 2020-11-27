package com.xingkong.spingboot.version2x.mongodb;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * * @className: role
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/20 11:42
 **/
@Document
@Data
public class Role implements Serializable {

    private Long id;

    @Field(value = "role_name")
    private String roleName;

    private String note;
}
