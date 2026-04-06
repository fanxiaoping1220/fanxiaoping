package com.langchain4j2.service.impl;

import com.langchain4j2.dao.LostRegisterDao;
import com.langchain4j2.entity.LostRegister;
import com.langchain4j2.service.LostRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 失物登记表(LostRegister)表服务实现类
 *
 * @author makejava
 * @since 2026-04-06 15:10:41
 */
@RequiredArgsConstructor
@Service
public class LostRegisterServiceImpl implements LostRegisterService {

    private final LostRegisterDao lostRegisterDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LostRegister queryById(Long id) {
        return this.lostRegisterDao.selectById(id);
    }

    /**
     * 新增数据
     *
     * @param lostRegister 实例对象
     * @return 实例对象
     */
    @Override
    public LostRegister insert(LostRegister lostRegister) {
        this.lostRegisterDao.insert(lostRegister);
        return lostRegister;
    }

    /**
     * 修改数据
     *
     * @param lostRegister 实例对象
     * @return 实例对象
     */
    @Override
    public LostRegister update(LostRegister lostRegister) {
        this.lostRegisterDao.updateById(lostRegister);
        return this.queryById(lostRegister.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.lostRegisterDao.deleteById(id) > 0;
    }
}
