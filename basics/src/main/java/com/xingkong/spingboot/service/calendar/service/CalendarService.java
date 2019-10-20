package com.xingkong.spingboot.service.calendar.service;

import com.alibaba.fastjson.JSONArray;

import java.time.LocalDate;

/**
 * 日历接口
 * Created by 46926 on 2019/10/20.
 */
public interface CalendarService {

    /**
     * 生成日历
     * @return
     */
    String createCalendar();

    /**
     * 根据时间段查询日历
     * @param startTime
     * @param endTime
     * @return
     */
    JSONArray getCalendar(LocalDate startTime, LocalDate endTime);
}
