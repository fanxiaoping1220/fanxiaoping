package com.xingkong.spingboot.service.excel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;

import java.io.File;
import java.io.InputStream;

/**
 * @className: ImageDateDO
 * @description: 图片导出的4种方式
 * @author: 范小平
 * @date: 2019-10-31 17:34
 * @version: 1.0.0
 */
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class ImageDateDO {

    private File file;

    private InputStream inputStream;

    @ExcelProperty(converter = StringImageConverter.class)
    private String string;

    private byte[] byteArray;

    public ImageDateDO() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }
}