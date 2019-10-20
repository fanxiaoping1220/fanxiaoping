package com.xingkong.spingboot.controller.calendar;

import com.xingkong.spingboot.service.calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: CalendarController
 * @Description: 日期API
 * @Auther: fanxiaoping
 * @Date: 2019/10/20 10:30
 * @version: 1.0.0
 */
@RestController
@RequestMapping(value = "/calendar")
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    /**
     * 自动生成日历
     * 每年1月1日0点执行
     * @return
     */
    @Scheduled(cron = "0 0 0 1 1 ? *")
    @PostMapping(value = "/createCalendar")
    public String createCalendar(){
        return calendarService.createCalendar();
    }
}
