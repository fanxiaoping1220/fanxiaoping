package com.xingkong.spingboot.entity;

import java.time.LocalDateTime;

/**
 * @className: ApolloApp
 * @description: 阿波罗Apollo ----APP
 * @author: 范小平
 * @date: 2019-09-08 10:05
 * @version: 1.0.0
 */
public class ApolloApp {

    /**
     * pk
     */
    private Integer id;

    /**
     * AppId
     */
    private String appId;

    /**
     * 应用名
     */
    private String name;

    /**
     * 部门id
     */
    private String orgId;

    /**
     * 部门名字
     */
    private String orgName;

    /**
     * ownerName
     */
    private String  ownerName;

    /**
     * ownerEmail
     */
    private String ownerEmail;

    /**
     * 是否删除
     * 1删除 0否
     */
    private Boolean isDelete;

    /**
     * 创建人邮箱前缀
     */
    private String dataChangeCreatedBy;

    /**
     * 创建时间
     */
    private LocalDateTime dataChangeCreatedTime;

    /**
     * 最后修改人邮箱前缀
     */
    private String dataChangeLastModifiedBy;

    /**
     * 最后修改时间
     */
    private LocalDateTime dataChangeLastTime;

    public ApolloApp() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getDataChangeCreatedBy() {
        return dataChangeCreatedBy;
    }

    public void setDataChangeCreatedBy(String dataChangeCreatedBy) {
        this.dataChangeCreatedBy = dataChangeCreatedBy;
    }

    public String getDataChangeLastModifiedBy() {
        return dataChangeLastModifiedBy;
    }

    public void setDataChangeLastModifiedBy(String dataChangeLastModifiedBy) {
        this.dataChangeLastModifiedBy = dataChangeLastModifiedBy;
    }

    public LocalDateTime getDataChangeLastTime() {
        return dataChangeLastTime;
    }

    public void setDataChangeLastTime(LocalDateTime dataChangeLastTime) {
        this.dataChangeLastTime = dataChangeLastTime;
    }

    public LocalDateTime getDataChangeCreatedTime() {
        return dataChangeCreatedTime;
    }

    public void setDataChangeCreatedTime(LocalDateTime dataChangeCreatedTime) {
        this.dataChangeCreatedTime = dataChangeCreatedTime;
    }
}