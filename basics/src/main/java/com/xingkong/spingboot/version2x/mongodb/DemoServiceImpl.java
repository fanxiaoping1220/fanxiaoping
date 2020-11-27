package com.xingkong.spingboot.version2x.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * * @className: DemoServiceImpl
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/20 11:48
 **/
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public int add(Demo demo) {
        mongoTemplate.insert(demo);
        return 0;
    }

    @Override
    public Demo getById(Long id) {
        Demo demo = mongoTemplate.findById(id, Demo.class);
        return demo;
    }

    @Override
    public List<Demo> findDemo(String userName, String note, Integer skip, Integer limit) {
        return null;
    }

    @Override
    public int update(Demo demo) {
//        mongoTemplate.update(demo);
        return 0;
    }

    @Override
    public int deleteById(Long id) {
        mongoTemplate.remove(id);
        return 0;
    }
}
