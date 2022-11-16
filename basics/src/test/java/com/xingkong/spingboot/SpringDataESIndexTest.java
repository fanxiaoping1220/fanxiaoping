package com.xingkong.spingboot;

import com.xingkong.spingboot.elasticsearch.dao.ProductDao;
import com.xingkong.spingboot.elasticsearch.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexBoost;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * * @className: SpringDataESIndexTest
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2022/11/16 0016 14:20
 **/

@SpringBootTest
public class SpringDataESIndexTest {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Autowired
    private ProductDao productDao;

    /**
     * 创建索引
     */
    @Test
    public void createIndex(){
        System.out.println("创建索引");
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex(){
        boolean delete = restTemplate.indexOps(Product.class).delete();
        System.out.println("删除索引"+delete);
    }

    /**
     * 插入
     */
    @Test
    public void save(){
        Product product = new Product();
        product.setId(1L);
        product.setCategory("手机");
        product.setTitle("华为手机");
        product.setPrice(6880.0);
        product.setImages("https://123456.jpg");
        productDao.save(product);
    }

    /**
     * 更新
     */
    @Test
    public void update(){
        Product product = new Product();
        product.setId(2L);
        product.setCategory("手机");
        product.setTitle("小米手机");
        product.setPrice(2998.0);
        product.setImages("https://123456.jpg");
        productDao.save(product);
    }

    /**
     * 根据id查询
     */
    @Test
    public void getId(){
        Product product = productDao.findById(1L).get();
        System.out.println(product);
    }

    /**
     * 查询所有
     */
    @Test
    public void getAll(){
        Iterable<Product> iterable = productDao.findAll();
        iterable.forEach(product -> {
            System.out.println(product);
        });
    }

    /**
     * 根据id删除
     */
    @Test
    public void deleteById(){
        productDao.deleteById(1L);
    }

    /**
     * 根据对象删除
     */
    @Test
    public void deleteByProduce(){
        Product product = new Product();
        product.setId(1L);
        productDao.delete(product);
    }

    /**
     * 删除所有
     */
    @Test
    public void deleteAll(){
        productDao.deleteAll();
    }

    /**
     * 根据多个id删除
     */
    @Test
    public void deleteIdList(){
        productDao.deleteAllById(asList(1L,2L));
    }

    /**
     * 批量保存
     */
    @Test
    public void batchSave(){
        List<Product> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Product product = new Product();
            product.setId(i+1L);
            product.setTitle("小米"+product.getId()+"手机");
            product.setCategory("手机");
            product.setPrice(200 + product.getId() * 100.0);
            product.setImages("https://123456"+product.getId()+".jpg");
            list.add(product);
        }
        productDao.saveAll(list);
    }

    /**
     * 分页加排序
     */
    @Test
    public void selectPageSort(){
        Page<Product> page = productDao.findAll(PageRequest.of(0, 20,Sort.by("id").descending()));
        System.out.println(page.getTotalPages());
        System.out.println(page.getPageable());
        page.getContent().forEach(product -> {
            System.out.println(product);
        });
    }
}
