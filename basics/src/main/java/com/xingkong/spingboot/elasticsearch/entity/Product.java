package com.xingkong.spingboot.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * * @className: Product
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/11/16 0016 14:07
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "product")
@Setting(shards = 3,replicas = 2)
@Accessors(chain = true)
@ToString
public class Product {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text)
    private String title;

    /**
     * 分类
     */
    @Field(type = FieldType.Keyword)
    private String category;

    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    private Double price;

    /**
     * 图片地址
     */
    @Field(type = FieldType.Keyword,index = false)
    private String images;

}
