package com.langchain4j2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductInfo {

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品库存
     */
    private Boolean isStock;

    /**
     * 配送时间
     */
    private String deliveryTime;
}
