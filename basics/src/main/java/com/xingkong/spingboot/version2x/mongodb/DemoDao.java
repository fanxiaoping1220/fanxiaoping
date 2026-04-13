package com.xingkong.spingboot.version2x.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * * @className: DemoDao
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/27 17:13
 **/
//标注为dao
@Repository
public interface DemoDao extends MongoRepository<Demo,Long> {

    /**
     * 符合JPA规范命名方法，则不需要在实现该方法也可用意在对满足条件的文档按照用户名进行模糊查询
     * @param userName 用户名称
     * @return 满足条件的用户信息
     */
    List<Demo> findByUserNameLike(String userName);
}
