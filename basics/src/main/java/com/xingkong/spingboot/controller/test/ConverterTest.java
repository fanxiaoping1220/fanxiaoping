package com.xingkong.spingboot.controller.test;

import com.xingkong.spingboot.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * * @className: ConverterTest
 * * @description: 转换器测试
 * * @author: fanxiaoping
 * * @date: 2020/12/17 10:46
 **/
@RequestMapping(value = "/converter")
@RestController
public class ConverterTest {

    /**
     * 数据
     * 1-user-2020/01/17 10:49:12
     * @param user
     * @return
     */
    @GetMapping(value = "/getUserByConverter")
    public User getUserByConverter(User user){
        return user;
    }

    /**
     * 数据
     * 1-user-2020/01/17 10:49:12,2-user-2020/12/17 10:49:12,3-user-2020/02/17 10:49:12
     * StringToCollectionConverter通过逗号分隔-->然后到StringToUserConverter-->最后到getList
     * @param userList
     * @return
     */
    @GetMapping(value = "/getList")
    public List<User> getList(List<User> userList){
        return userList;
    }
}
