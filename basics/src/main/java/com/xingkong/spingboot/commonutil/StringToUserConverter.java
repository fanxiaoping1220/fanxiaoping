package com.xingkong.spingboot.commonutil;

import com.xingkong.spingboot.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * * @className: StringToUserConverter
 * * @description: 自定义字符串转换器 {id}-{name}-{createTime}
 * * @author: fanxiaoping
 * * @date: 2020/12/17 10:34
 **/
@Component
public class StringToUserConverter implements Converter<String,User> {

    /**
     * 转换方法 请求先到StringToUserConverter -> ConverterTest.getUserByConverter
     * @param s
     * @return
     */
    @Override
    public User convert(String s) {
        String[] split = s.split("-");
        User user = new User();
        user.setId(Integer.valueOf(split[0]));
        user.setName(split[1]);
        user.setCreateTime(LocalDateTime.parse(split[2], DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        return user;
    }
}
