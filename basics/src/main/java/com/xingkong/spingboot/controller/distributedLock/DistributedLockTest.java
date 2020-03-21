package com.xingkong.spingboot.controller.distributedLock;

import com.xingkong.spingboot.commonutil.RedisPoolUtil;
import com.xingkong.spingboot.commonutil.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author fanxiaoping
 * @className: DistributedLockTest
 * @description:
 * @date 2020-03-21 16:44:12
 */
@RestController
@RequestMapping(value = "/distributedLock")
public class DistributedLockTest {

    @Autowired
    private RedisPoolUtil redisPoolUtil;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "/test")
    public void test(){
        String s = UUID.randomUUID().toString();
        boolean a = redisPoolUtil.tryGetDistributedLock("A",s, 1000);
        System.out.println(s);
        System.out.println(a);
        String w = UUID.randomUUID().toString();
        boolean b = redisPoolUtil.tryGetDistributedLock("A", w, 1000);
        System.out.println(w);
        System.out.println(b);
        boolean a1 = redisPoolUtil.releaseDistributedLock("A", s);
        System.out.println(a1);
        boolean a2 = redisPoolUtil.releaseDistributedLock("A", w);
        System.out.println(a2);
        redisUtil.set("aaa","123456");
        System.out.println((String) redisUtil.get("aaa"));
    }
}
