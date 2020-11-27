package com.xingkong.spingboot.version2x.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * * @className: DemoController
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/27 15:09
 **/
@RequestMapping(value = "/mongodb")
@RestController
public class MongodbController {

    @Autowired
    private DemoService demoService;

    @GetMapping(value = "/add")
    public int add(){
        Demo demo = new Demo();
        demo.setId(1L);
        demo.setUserName("fanxp");
        demo.setNote("note");
        return demoService.add(demo);
    }

    @GetMapping(value = "/getById")
    public Demo getById(@RequestParam("id") Long id){
        return demoService.getById(id);
    }

    @PostMapping(value = "/update")
    public int update(@RequestBody Demo demo){
        return demoService.update(demo);
    }

    @DeleteMapping(value = "/deleteById")
    public int deleteById(@RequestParam("id") Long id){
        return demoService.deleteById(id);
    }
}
