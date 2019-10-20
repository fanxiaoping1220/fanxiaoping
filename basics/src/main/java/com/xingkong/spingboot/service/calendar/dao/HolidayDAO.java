package com.xingkong.spingboot.service.calendar.dao;

import com.xingkong.spingboot.service.calendar.entity.HolidayDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @ClassName: HolidayDAO
 * @Description: 节假日sql
 * @Auther: fanxiaoping
 * @Date: 2019/10/20 10:03
 * @version: 1.0.0
 */
@Mapper
public interface HolidayDAO {

    /**
     * 批量插入
     * @param list
     * @return
     */
    @Insert("<script>" +
            "insert into holiday (date,type,description) values " +
            "<foreach item = 'item' collection = 'list' separator = ',' >" +
            "(#{item.date},#{item.type},#{item.description})" +
            "</foreach>" +
            "</script>")
    int branchInsert(@Param("list") List<HolidayDO> list);

    /**
     * 根据时间查询
     * @param startTime
     * @param endTime
     * @return
     */
    @Select("select * from holiday where date >= #{startTime} and date <= #{endTime}")
    List<HolidayDO> getByDate(@Param("startTime") LocalDate startTime, @Param("endTime") LocalDate endTime);
}
