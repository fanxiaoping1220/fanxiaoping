package com.xingkong.spingboot.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class User {

    private Integer id;

    private String name;

    private LocalDateTime createTime;
}
