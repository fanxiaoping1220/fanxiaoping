package com.xingkong.spingboot.elasticsearch.dao;

import com.xingkong.spingboot.elasticsearch.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * * @className: ProductDao
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/11/16 0016 14:11
 **/
public interface ProductDao extends ElasticsearchRepository<Product, Long> {

    /**
     * 根据category查询
     * @param title
     * @return
     */
    List<Product> queryByTitle(String title);

    /**
     * 根据category 并分页
     * @param category
     * @param pageable
     * @return
     */
    Page<Product> queryByCategory(String category, Pageable pageable);
}
