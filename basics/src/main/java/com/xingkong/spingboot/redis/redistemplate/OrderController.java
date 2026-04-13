package com.xingkong.spingboot.redis.redistemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * * @className: OrderController
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/9/25 0025 11:46
 **/
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 新增订单
     */
    @PostMapping(value = "/order/add")
    public void addOrder(){
        orderService.addOrder();
    }

    /**
     * 按照keyId查询订单
     * @param keyId
     * @return
     */
    @GetMapping(value = "/order/{keyId}")
    public String getOrderById(@PathVariable(name = "keyId") Integer keyId){
        return orderService.getOrderById(keyId);
    }
}
