package com.xingkong.spingboot.controller.excel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * String and string converter
 *
 * @author fanxiaoping
 */
public class CustomStringStringConverter implements Converter<LocalTime> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里是读的时候会调用 不用管
     *
     * @param cellData
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     */
    @Override
    public LocalTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        return null;
    }

    /**
     * 这里是写的时候会调用 不用管
     *
     * @param value
     *            NotNull
     * @param contentProperty
     *            Nullable
     * @param globalConfiguration
     *            NotNull
     * @return
     */
    @Override
    public CellData convertToExcelData(LocalTime value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration) {
        return new CellData(value.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

}
