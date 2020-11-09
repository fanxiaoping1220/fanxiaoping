package com.xingkong.spingboot.version2x.transactional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * * @className: UserDao
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/9 14:59
 **/
@Mapper
public interface UserDao {

    @Insert("insert into user (id,name,note)values(#{user.id},#{user.name},#{user.note})")
    int insert(@Param("user") User user);
}
