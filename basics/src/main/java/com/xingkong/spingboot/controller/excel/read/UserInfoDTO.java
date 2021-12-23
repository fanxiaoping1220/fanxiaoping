package com.xingkong.spingboot.controller.excel.read;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author fanxiaoping
 * @since 2021-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserInfoDTO implements Serializable {

    private static final long serialVersionUID=1L;

      @ExcelIgnore
    private Long id;

    /**
     * 登录密码
     */
    @ExcelIgnore
    private String password;

    /**
     * 姓名
     */
    @ExcelIgnore
    private String name;

    /**
     * 电话
     */
    @ExcelIgnore
    private String phone;

    /**
     * 工号
     */
    @ExcelProperty(index = 0)
    private String jobNumber;

    /**
     * 头像地址
     */
    @ExcelIgnore
    private String photoUrl;

    /**
     * 分局id
     */
    @ExcelIgnore
    private Long superId;

    /**
     * 支局id
     */
    @ExcelIgnore
    private Long childId;

    /**
     * 资料是否完善
     */
    @ExcelIgnore
    private Boolean dataIsPerfect;

    /**
     * 所属操作系统 前端0 后台1
     */
    @ExcelProperty(index = 2)
    private Boolean belongSystem;

    /**
     * 创建时间
     */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ExcelIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @ExcelIgnore
    private Boolean isDelete;

    /**
     * 每页显示条数
     */
    @ExcelIgnore
    private Integer pageNumber;

    /**
     * 当前页
     */
    @ExcelIgnore
    private Integer pageSize;

    /**
     * 角色id
     */
    @ExcelProperty(index = 1)
    private Long roleId;


}
