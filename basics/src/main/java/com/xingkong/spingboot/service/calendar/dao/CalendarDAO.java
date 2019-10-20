package com.xingkong.spingboot.service.calendar.dao;

import com.xingkong.spingboot.service.calendar.entity.CalendarDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @ClassName: CalendarDAO
 * @Description: 日期sql
 * @Auther: fanxiaoping
 * @Date: 2019/10/20 10:03
 * @version: 1.0.0
 */
@Mapper
public interface CalendarDAO {

    /**
     * 批量插入
     * @param list
     * @return
     */
    @Insert("<script>" +
            "insert into calendar(date,type) values " +
            "<foreach item = 'item' collection = 'list' separator = ','>" +
            "(#{item.date},#{item.type}) " +
            "</foreach>" +
            "</script>")
    int branchInsert(@Param("list") List<CalendarDO> list);

    /**
     * 根据时间段查询
     * @param StartTime
     * @param endTime
     * @return
     */
    @Select("select * from calendar where date >= #{startTime} and date <= #{endTime}")
    List<CalendarDO> getByDate(@Param("startTime") LocalDate StartTime,@Param("endTime") LocalDate endTime);
}
