package com.xingkong.spingboot.entity;

import java.time.LocalDateTime;

/**
 * @ClassName: Artist
 * @Description: 艺术家model
 * @Auther: fanxiaoping
 * @Date: 2019/6/8 18:06
 * @version: 1.0.0
 */
public class Artist {

    /**
     * 姓名
     */
    private String name;

    /**
     * 国籍
     */
    private String nationality;

    /**
     * 数字
     */
    private Integer number;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Artist() {
    }

    public Artist(String name, String nationality, LocalDateTime createTime) {
        this.name = name;
        this.nationality = nationality;
        this.createTime = createTime;
    }

    public Artist(Integer number) {
        this.number = number;
    }

    public Artist(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
