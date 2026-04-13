package com.xingkong.spingboot.service;


import com.xingkong.spingboot.entity.Product;

import java.util.List;

/**
 * * @className: JHSTaskService
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/12/6 0006 16:59
 **/
public interface JHSTaskService {

    /**
     * 偷个懒不加mysql了,模拟从数据库去20件特价商品,用于加载到聚划算的页面中
     * @return
     */
    List<Product> getProductsFromMysql();
}
