package com.xingkong.spingboot.version2x.transactional;

import org.apache.ibatis.annotations.*;

/**
 * * @className: UserDao
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/9 14:59
 **/
@Mapper
public interface UserDao {

    @Insert({"insert into user (name,note)values(#{user.name},#{user.note})"})
    //主键id自动写入，并回填
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insert(@Param("user") User user);


    @Select({
            "select * from user where id = #{id}"
    })
    User getById(@Param("id") Long id);

    @Update({
            "update user set name = #{user.name}, note = #{user.note}  where id = #{user.id} "
    })
    int update(@Param("user") User user);

    @Delete({"delete form user where id = #{id}"})
    int deleteById(@Param("id") Long id);
}
