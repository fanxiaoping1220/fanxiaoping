package com.xingkong.spingboot.version2x.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * * @className: UserController
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/10/29 15:15
 **/
@RequestMapping(value = "/user")
@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping(value = "/printUser")
    public User printUser(){
        User user = new User();
        user.setId(1);
        user.setUserName("ffff");
        user.setNote("note");
        usersService.printUser(user);
        return user;
    }
}
