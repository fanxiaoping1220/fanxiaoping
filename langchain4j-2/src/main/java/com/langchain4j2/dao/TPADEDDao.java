package com.langchain4j2.dao;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.langchain4j2.entity.slave.TPADED;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lang
 * @description 针对表【TPADED(分类扩展表)】的数据库操作Mapper
 * @createDate 2023-07-04 11:39:09
 */
@DS("slave")
@Mapper
public interface TPADEDDao extends BaseMapper<TPADED> {
}
