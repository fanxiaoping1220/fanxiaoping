package com.xingkong.spingboot.controller.calendar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xingkong.spingboot.commonutil.Consts;
import com.xingkong.spingboot.service.calendar.service.CalendarService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

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

    /**
     * 根据时间段查询日历
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getCalendar")
    public JSONArray getCalendar(@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime){
        if(StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)){
            JSONObject json = new JSONObject();
            json.put("message","开始时间或者结束时间不能为空");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(json);
            return jsonArray;
        }
        if(!Pattern.matches(Consts.REGEX,startTime) || !Pattern.matches(Consts.REGEX,endTime)){
            JSONObject json = new JSONObject();
            json.put("message","开始时间或者结束时间格式不正确，正确格式为yyyy-MM-dd 如:2019-05-02");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(json);
            return jsonArray;
        }
        return calendarService.getCalendar(LocalDate.parse(startTime,
                DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                LocalDate.parse(endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
