package com.xingkong.spingboot.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * * @className: a
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020-04-23  15:11
 **/
@Data
@ToString
/*全参构造方法*/
@AllArgsConstructor
/*无参构造方法*/
@NoArgsConstructor
public class Test {

    private String name;

    private Integer code;
}
