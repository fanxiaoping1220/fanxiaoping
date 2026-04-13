package com.langchain4j.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.langchain4j.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (Reservation)表数据库访问层
 *
 * @author makejava
 * @since 2026-03-12 15:01:57
 */
@Mapper
public interface ReservationDao extends BaseMapper<Reservation> {

}

