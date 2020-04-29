package com.xingkong.spingboot.service.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.converters.Converter;
import com.xingkong.spingboot.controller.excel.converter.CustomStringStringConverter;

import java.time.LocalTime;

/**
 * @className: ConverterDataDO
 * @description:
 * @author: 范小平
 * @date: 2019-10-31 16:25
 * @version: 1.0.0
 */
public class ConverterDataDO {

    @ExcelProperty(value = "字符串标题")
    @ColumnWidth(value = 30)
    private String string;

    /***
     * 格式转换
     * LocalTime LocalDate LocalDateTime 需要重写{@link Converter}
     * Date 可以直接用 {@link com.alibaba.excel.annotation.format.DateTimeFormat}
     */
    @ExcelProperty(value = "日期", converter = CustomStringStringConverter.class)
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ColumnWidth(value = 30)
    private LocalTime date;

    @ExcelProperty(value = "数字")
    @NumberFormat(value = "#.##%")
    @ColumnWidth(value = 30)
    private Double doubleDate;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public LocalTime getDate() {
        return date;
    }

    public void setDate(LocalTime date) {
        this.date = date;
    }

    public Double getDoubleDate() {
        return doubleDate;
    }

    public void setDoubleDate(Double doubleDate) {
        this.doubleDate = doubleDate;
    }
}