package com.xingkong.spingboot.elasticsearch.dao;

import com.xingkong.spingboot.elasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * * @className: ProductDao
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/11/16 0016 14:11
 **/
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
