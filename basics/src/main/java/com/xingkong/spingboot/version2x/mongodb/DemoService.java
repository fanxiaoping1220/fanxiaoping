package com.xingkong.spingboot.version2x.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

/**
 * * @className: DempService
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/20 11:48
 **/
public interface DemoService {

    /**
     * 添加
     * @param demo
     * @return
     */
    int add(Demo demo);

    /**
     * 查看
     * @param id
     * @return
     */
    Demo getById(Long id);

    /**
     * 查询
     * @param userName
     * @param note
     * @param skip 跳过用户个数
     * @param limit 限制返回条数
     * @return
     */
    List<Demo> findDemo(String userName,String note,Integer skip,Integer limit);

    /**
     * 更新
     * @param demo
     * @return
     */
    UpdateResult update(Demo demo);

    /**
     * 删除
     * @param id
     * @return
     */
    DeleteResult deleteById(Long id);

}
