package com.xingkong.spingboot.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * * @className: Content
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/11/2 0002 10:14
 **/
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Content {

    private String title;

    private String price;

    private String image;
}
