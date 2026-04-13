package com.langchain4j.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Reservation)实体类
 *
 * @author makejava
 * @since 2026-03-12 15:01:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements Serializable {

    /**
     * pk
     */
    @Id
    private Long id;
    /**
     * 考生姓名
     */
    private String name;
    /**
     * 考生性别
     */
    private String gender;
    /**
     * 考生手机号
     */
    private String phone;
    /**
     * 沟通时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime communicationTime;
    /**
     * 省份
     */
    private String province;
    /**
     * 考生预估分数
     */
    private Integer estimatedScore;
}

