package com.langchain4j2.service;

import com.langchain4j2.entity.LostRegister;

/**
 * 失物登记表(LostRegister)表服务接口
 *
 * @author makejava
 * @since 2026-04-06 15:10:41
 */
public interface LostRegisterService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LostRegister queryById(Long id);


    /**
     * 新增数据
     *
     * @param lostRegister 实例对象
     * @return 实例对象
     */
    LostRegister insert(LostRegister lostRegister);

    /**
     * 修改数据
     *
     * @param lostRegister 实例对象
     * @return 实例对象
     */
    LostRegister update(LostRegister lostRegister);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
