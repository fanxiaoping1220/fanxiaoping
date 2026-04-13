package com.langchain4j2.entity.slave;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.langchain4j2.entity.handler.SqlServerLocalDateTimeTypeHandler;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("TPADED")
public class TPADED {

    /**
    * 分类方式
     */
    @TableField("DED001")
    private  String DED001;
    /**
    * 分类编号
     */
    @TableField("DED002")
    private  String DED002;
    /**
    * 分类名称
    */
    @TableField("DED003")
     private  String DED003;
    /**
    * 预留字段
    */
    @TableField("DED004")
    private  String DED004;
    /**
    * 预留字段
    */
    @TableField("DED005")
    private  String DED005;
    /**
    * 成本计价方式
    */
    @TableField("DED006")
     private  String DED006;
    /**
    * 分类级次
    */
    @TableField("DED007")
    private  int DED007;
    /**
    * 分类层级
    */
    @TableField("DED008")
    private  String DED008;
    /**
    * 可用否
    */
    @TableField("DED009")
     private  String DED009;
    /**
    * 对应编码
    */
    @TableField("DED010")
    private  String DED010;
    /**
    * 商品利润率
    */
    @TableField("DED011")
    private  double DED011;
    /**
    * 录入者编号
    */
    @TableField("DED901")
    private  String DED901;
    /**
    * 录入时间
    */
    @TableField(value = "DED902",typeHandler = SqlServerLocalDateTimeTypeHandler.class)
    private LocalDateTime DED902;
    /**
    * 更改者编号
    */
    @TableField(value = "DED903")
    private String DED903;
    /**
    * 更改时间
    */
    @TableField(value = "DED904",typeHandler = SqlServerLocalDateTimeTypeHandler.class)
    private LocalDateTime DED904;
    /**
    * 更新标记
    */
    @TableField("DED905")
    private  int DED905;
}
