package com.xingkong.spingboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * * @className: Product
 * * @description:模拟聚划算活动product信息
 * * @author: fan xiaoping
 * * @date: 2023/12/6 0006 16:49
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 产品详情
     */
    private String detail;
}
