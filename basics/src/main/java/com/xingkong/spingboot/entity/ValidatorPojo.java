package com.xingkong.spingboot.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * * @className: ValidatorPojo
 * * @description: 数据验证模型  配合 @Valid 验证使用 Errors
 * * @author: fanxiaoping
 * * @date: 2020/12/18 11:09
 **/
@Data
public class ValidatorPojo {

    @NotNull(message = "id不能为空")
    private Long id;

    @Future(message = "需要一个将来的日期")//只能是将来的日期
    @DateTimeFormat(pattern = "yyyy-MM-dd",iso = DateTimeFormat.ISO.DATE)
    @NotNull
    private LocalDate date;

    @DecimalMin(value = "0.1",message = "最小值为0.1")
    @DecimalMax(value = "10000.00",message = "最大值为10000")
    @NotNull
    private Double doubleValue;

    @Min(value = 1,message = "最小值为1")
    @Max(value = 1000,message = "最大值为1000")
    @NotNull
    private Integer integerValue;

    @Range(min = 1,max = 1000,message = "范围为1到1000之间")
    private Long range;

    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 20,max = 30,message = "字符串长度要求20到30之间")
    private String size;
}
