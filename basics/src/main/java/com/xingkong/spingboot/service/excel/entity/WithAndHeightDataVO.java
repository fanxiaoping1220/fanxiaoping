package com.xingkong.spingboot.service.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import java.util.Date;

/**
 * @ClassName: WithAndHeightDataVO
 * @Description:
 * @Auther: fanxiaoping
 * @Date: 2019/11/2 17:00
 * @version: 1.0.0
 */
@ContentRowHeight(value = 30)
@HeadRowHeight(value = 20)
@ColumnWidth(value = 15)
public class WithAndHeightDataVO {

    @ExcelProperty(value = "标题")
    private String string;

    @ExcelProperty(value = "日期")
    @ColumnWidth(value = 30)
    private Date date;

    @ExcelProperty(value = "数字")
    private Double doubleData;

    public WithAndHeightDataVO() {
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getDoubleData() {
        return doubleData;
    }

    public void setDoubleData(Double doubleData) {
        this.doubleData = doubleData;
    }
}
