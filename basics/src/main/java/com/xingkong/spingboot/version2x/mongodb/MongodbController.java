package com.xingkong.spingboot.version2x.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;

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
        demo.setId(2L);
        demo.setUserName("fxp");
        demo.setNote("note");
        return demoService.add(demo);
    }

    @GetMapping(value = "/getById")
    public Demo getById(@RequestParam("id") Long id){
        return demoService.getById(id);
    }

    @PostMapping(value = "/update")
    public UpdateResult update(@RequestBody Demo demo){
        return demoService.update(demo);
    }

    @DeleteMapping(value = "/deleteById")
    public DeleteResult deleteById(@RequestParam("id") Long id){
        return demoService.deleteById(id);
    }

    @GetMapping(value = "/findDemo")
    public List<Demo> findDemo(@RequestParam("userName") String userName, @RequestParam("note") String note, @RequestParam("skip") Integer skip, @RequestParam("limit") Integer limit){
        return demoService.findDemo(userName,note,skip,limit);
    }
}
