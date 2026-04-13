package com.xingkong.spingboot.version2x.aop;

import org.springframework.stereotype.Service;

/**
 * * @className: UserServiceImpl
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/10/29 14:54
 **/
@Service
public class UsersServiceImpl implements UsersService {

    @Override
    public void printUser(User user) {
        if(user == null){
            throw  new RuntimeException("用户参数不能为空");
        }
        System.out.println(user.toString());
    }
}
