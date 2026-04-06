package com.langchain4j2.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.langchain4j2.entity.LostRegister;
import org.apache.ibatis.annotations.Mapper;

/**
 * 失物登记表(LostRegister)表数据库访问层
 *
 * @author makejava
 * @since 2026-04-06 15:10:40
 */
@Mapper
public interface LostRegisterDao extends BaseMapper<LostRegister> {

}

