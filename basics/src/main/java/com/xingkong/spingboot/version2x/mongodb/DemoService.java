package com.xingkong.spingboot.version2x.mongodb;

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
     * @param skip
     * @param limit
     * @return
     */
    List<Demo> findDemo(String userName,String note,Integer skip,Integer limit);

    /**
     * 更新
     * @param demo
     * @return
     */
    int update(Demo demo);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteById(Long id);

}
