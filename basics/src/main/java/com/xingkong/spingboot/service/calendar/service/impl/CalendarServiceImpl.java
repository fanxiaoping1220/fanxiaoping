package com.xingkong.spingboot.service.calendar.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.xingkong.spingboot.controller.calendar.enums.TypeEnum;
import com.xingkong.spingboot.controller.calendar.vo.CalendarVO;
import com.xingkong.spingboot.service.calendar.dao.CalendarDAO;
import com.xingkong.spingboot.service.calendar.dao.HolidayDAO;
import com.xingkong.spingboot.service.calendar.entity.CalendarDO;
import com.xingkong.spingboot.service.calendar.entity.HolidayDO;
import com.xingkong.spingboot.service.calendar.service.CalendarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: CalendarServiceImpl
 * @Description:
 * @Auther: fanxiaoping
 * @Date: 2019/10/20 10:33
 * @version: 1.0.0
 */
@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private CalendarDAO calendarDAO;

    @Autowired
    private HolidayDAO holidayDAO;

    @Override
    public String createCalendar() {
        //1.获取今年的假节日
        LocalDate now = LocalDate.now();
        LocalDate startTime = LocalDate.of(now.getYear(), 1, 1);
        LocalDate endTime = LocalDate.of(now.getYear(),12,31);
        List<HolidayDO> holidayList = holidayDAO.getByDate(startTime, endTime);
        Map<LocalDate, HolidayDO> holidayMap = holidayList.stream().collect(Collectors.toMap(o -> o.getDate(), o -> o));
        //2.生成日历
        List<CalendarDO> addList = new ArrayList<>();
        LocalDate monthFirstDay;
        LocalDate monthLastDay;
        for(int i = 0 ; i < 12; i++){
            if(i == 0){
                monthFirstDay = startTime;
            }else {
                monthFirstDay = startTime.plusMonths(i);
            }
            monthLastDay = monthFirstDay.with(TemporalAdjusters.lastDayOfMonth());
            Period between = Period.between(monthFirstDay, monthLastDay);
            LocalDate date;
            for(int j = 0; j <= between.getDays(); j++){
                if(j == 0){
                    date = monthFirstDay;
                }else {
                    date = monthFirstDay.plusDays(j);
                }
                CalendarDO calendarDO = new CalendarDO();
                calendarDO.setDate(date);
                if(holidayMap.containsKey(date)){
                    HolidayDO holidayDO = holidayMap.get(date);
                    calendarDO.setType(holidayDO.getType());
                    calendarDO.setDescription(holidayDO.getDescription());
                }else {
                    calendarDO.setType(TypeEnum.WORK_DAY.getCode());
                }
                addList.add(calendarDO);
            }

        }
        if(addList.size() > 0){
            calendarDAO.branchInsert(addList);
        }
        return "success";
    }

    @Override
    public JSONArray getCalendar(LocalDate startTime, LocalDate endTime) {
        List<CalendarDO> calendarList = calendarDAO.getByDate(startTime, endTime);
        List<CalendarVO> list = new ArrayList<>();
        calendarList.forEach(calendarDO -> {
            CalendarVO calendarVO = new CalendarVO();
            BeanUtils.copyProperties(calendarDO,calendarVO);
            list.add(calendarVO);
        });
        return JSONArray.parseArray(JSONArray.toJSONString(list));
    }
}
