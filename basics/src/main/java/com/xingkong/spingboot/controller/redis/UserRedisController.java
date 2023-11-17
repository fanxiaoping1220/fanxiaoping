package com.xingkong.spingboot.controller.redis;

import com.xingkong.spingboot.entity.UserRedis;
import com.xingkong.spingboot.service.UserRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * * @className: UserRedisController
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/11/17 0017 17:07
 **/
@RequestMapping(value = "/redis/user")
@RestController
public class UserRedisController {

    @Autowired
    private UserRedisService userRedisService;

    /**
     * 添加
     * @param user
     */
    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody UserRedis user){
         userRedisService.addUser(user);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping(value = "/getUser/{id}")
    public UserRedis getUser(@PathVariable(name = "id") Long id){
        return userRedisService.getUser(id);
    }
}
