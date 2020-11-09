package com.xingkong.spingboot.version2x.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * * @className: UserController
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/9 15:08
 **/
@RequestMapping(value = "/userTran")
@RestController
public class UserTranController {

    @Autowired
    private UserBatchService userBatchService;

    @GetMapping(value = "/insertUsers")
    public Map<String,Object> insertUsers(String userName1,String note1,String userName2,String note2){
        User user1 = new User();
        user1.setName(userName1);
        user1.setNote(note1);
        user1.setId(1L);
        User user2 = new User();
        user2.setName(userName2);
        user2.setNote(note2);
        user2.setId(1L);
        List<User> users = asList(user1, user2);
        int i = userBatchService.insertUsers(users);
        Map<String,Object> result = new HashMap<>();
        result.put("success",i>0);
        result.put("user",users);
        return result;
    }
}
