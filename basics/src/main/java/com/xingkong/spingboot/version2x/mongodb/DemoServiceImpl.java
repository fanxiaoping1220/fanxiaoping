package com.xingkong.spingboot.version2x.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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
//        mongoTemplate.insert(demo);
        //表名一致 保存和更新共用，无则保存，有则更新 根据id判断
        mongoTemplate.save(demo);
        //表名不一致
//        mongoTemplate.save(demo,"demo");
        return 0;
    }

    @Override
    public Demo getById(Long id) {
        Demo demo = mongoTemplate.findById(id, Demo.class);
//        //构造查询条件
//        Criteria criteria =  Criteria.where("id").is(id);
//        //设置查询
//        Query query = Query.query(criteria);
//        Demo demo = mongoTemplate.findOne(query, Demo.class);
        return demo;
    }

    @Override
    public List<Demo> findDemo(String userName, String note, Integer skip, Integer limit) {
        //构造查询条件
        Criteria criteria = Criteria.where("userName").is(userName).and("note").is(note);
        //设置查询
        Query query = Query.query(criteria).skip(skip).limit(limit);
        List<Demo> list = mongoTemplate.find(query, Demo.class);
        return list;
    }

    /**
     * UpdateResult
     * matchedCount: 与对象匹配的文档数
     * modifiedCount：修改条数
     * upsertedId：如果存在因为更新而插入文档的情况会返回插入文档的信息
     * @param demo
     * @return
     */
    @Override
    public UpdateResult update(Demo demo) {
        //构建更新条件
        Query query = Query.query(Criteria.where("id").is(demo.getId()));
        //设置只更新字段
        Update update = Update.update("userName", demo.getUserName()).set("note", demo.getNote());
        UpdateResult result = mongoTemplate.updateFirst(query, update, Demo.class);
        return result;
    }

    @Override
    public DeleteResult deleteById(Long id) {
        Query query = Query.query(Criteria.where("id").is(id));
        DeleteResult result = mongoTemplate.remove(query, Demo.class);
        return result;
    }
}
