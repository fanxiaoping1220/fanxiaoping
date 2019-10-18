package com.xingkong.spingboot.commonutil;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className: LocalDateTimeUtil
 * @description: LocalDateTime工具类
 * @author: 范小平
 * @date: 2019-04-18 17:11
 * @version: 1.0.0
 */
public class LocalDateTimeUtil {

    /**
     * 将Date转换为LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime convertDateToLocalDateTime(Date date){
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 获取昨天日期
     * @return
     */
    public static String getYesterday(){
        LocalDate localDate = LocalDate.now();
        String date;
        //判断是不是==1日
        if(localDate.getDayOfMonth() == 1){
            //判断是不是==1月
            if(localDate.getMonth().equals(Month.JANUARY)){
                date = LocalDate.of(localDate.minusYears(1).getYear(),localDate.minusMonths(1).getMonth(),localDate.minusDays(1).getDayOfMonth()).toString();
            }else{
                date = LocalDate.of(localDate.getYear(),localDate.minusMonths(1).getMonth(),localDate.minusDays(1).getDayOfMonth()).toString();
            }
        }else{
            date = LocalDate.of(localDate.getYear(), localDate.getMonth(), localDate.minusDays(1).getDayOfMonth()).toString();
        }
        return date;
    }

    public static void main(String[] args) {
        //获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        System.out.println(convertDateToLocalDateTime(new Date()));
        //时间相减---》 小时，天，秒，分钟
        Duration duration = Duration.between(LocalDateTime.now(),LocalDateTime.of(2019,10,8,12,10));
        System.out.println(LocalDateTime.now());
        System.out.println(LocalDateTime.of(2019,10,8,12,10));
        //相减后的小时
        System.out.println(duration.toHours());
        //相减后的天
        System.out.println(duration.toDays());
        //相减后的秒
        System.out.println(duration.toMillis());
        //相减后的分钟
        System.out.println(duration.toMinutes());
        //时间相减 ---》 年 ，月
        Period period = Period.between(LocalDateTime.now().toLocalDate(),LocalDateTime.of(2019,10,19,12,10).toLocalDate());
        System.out.println(LocalDateTime.now().toLocalDate());
        System.out.println(LocalDateTime.of(2019,10,8,12,10).toLocalDate());
        //年
        System.out.println(period.getYears());
        //月
        System.out.println(period.getMonths());
        System.out.println(period.toTotalMonths());
        //获取当月第一天
        System.out.println(localDateTime.toLocalDate().with(TemporalAdjusters.firstDayOfMonth()));
        //获取当月最后一天
        System.out.println(localDateTime.toLocalDate().with(TemporalAdjusters.lastDayOfMonth()));
        // 取2017年1月第一个周一，用Calendar要死掉很多脑细胞：
        System.out.println(LocalDate.parse("2019-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)));
        //获取现在的时分秒
        System.out.println(LocalTime.now());
        //获取现在的日期
        System.out.println(LocalDate.now());
        //获取现在日期+时间
        System.out.println(LocalDateTime.now());

        /**
         * 生成这个月的时间
         */
        List<LocalDate> list = new ArrayList<>();
        LocalDate with = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        list.add(with);
        Period between = Period.between(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()));
        for(int i = 1 ; i <= between.getDays() ; i++){
            LocalDate localDate = with.plusDays(i);
            System.out.println(localDate);
            list.add(localDate);
        }
        System.out.println(list);

    }

}