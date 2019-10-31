package com.xingkong.spingboot.service.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

/**
 * @className: DemoDateDO
 * @description: 模板
 * @author: 范小平
 * @date: 2019-10-31 11:15
 * @version: 1.0.0
 */
public class ComplexDateDO {

    /**
     * 标题
     */
    @ExcelProperty(value = "标题",index = 0)
    @ColumnWidth(value = 15)
    private String title;

    /**
     * 日期
     */
    @ExcelProperty(value = "日期",index = 1)
    @ColumnWidth(value = 15)
    private String date;

    /**
     *数字标题
     */
    @ExcelProperty(value = "数字标题",index = 2)
    @ColumnWidth(value = 15)
    private Double doubleDate;

    public ComplexDateDO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getDoubleDate() {
        return doubleDate;
    }

    public void setDoubleDate(Double doubleDate) {
        this.doubleDate = doubleDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}