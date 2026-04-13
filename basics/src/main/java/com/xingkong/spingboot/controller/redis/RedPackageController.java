package com.xingkong.spingboot.controller.redis;

import com.xingkong.spingboot.service.RedPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * * @className: RedPackageController
 * * @description:模拟抢红包
 * * @author: fan xiaoping
 * * @date: 2024/4/29 0029 16:55
 **/
@RequestMapping(value = "/redis/redPackage")
@RestController
public class RedPackageController {

    @Autowired
    private RedPackageService redPackageService;

    /**
     * 模拟发红包
     * @param totalMoney 总金额
     * @param redPackageNumber 总个数
     * @return
     */
    @PostMapping(value = "/sendRedPackage")
    public String sendRedPackage(@RequestParam(name = "totalMoney") int totalMoney, @RequestParam(name = "redPackageNumber") int redPackageNumber){
        return redPackageService.sendRedPackage(totalMoney,redPackageNumber);
    }

    /**
     * 模拟抢红包
     * @param redPackageKey 红包key
     * @param userId 用户id
     * @return
     */
    @PostMapping(value = "/robRedPackage")
    public String robRedPackage(@RequestParam(name = "redPackageKey") String redPackageKey,@RequestParam(name = "userId") String userId){
        return redPackageService.robRedPackage(redPackageKey,userId);
    }
}
