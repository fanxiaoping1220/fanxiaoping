package com.xingkong.spingboot.mapper;

import com.xingkong.spingboot.entity.ApolloApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @className: ApolloAppDAO
 * @description: 阿波罗apolloapp-----sql
 * @author: 范小平
 * @date: 2019-09-08 10:14
 * @version: 1.0.0
 */
@Mapper
public interface ApolloAppDAO {

    @Results({
            @Result(column = "Id",property = "id"),@Result(column = "AppId",property = "appId"),@Result(column = "Name",property = "name"),
            @Result(column = "OrgId",property = "orgId"),@Result(column = "OrgName",property = "orgName"),@Result(column = "OwnerName",property = "ownerName"),
            @Result(column = "OwnerEmail",property = "ownerEmail"),@Result(column = "IsDelete",property = "isDelete"),@Result(column = "DataChange_CreatedBy",property = "dataChangeCreatedBy"),
            @Result(column = "DataChange_CreatedTime",property = "dataChangeCreatedTime"),@Result(column = "DataChange_LastModifiedBy",property = "dataChangeLastModifiedBy"),@Result(column = "DataChange_LastTime",property = "dataChangeLastTime"),
    })
    @Select("select * from  App")
    List<ApolloApp> getList();
}
