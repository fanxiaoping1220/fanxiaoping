package com.xingkong.spingboot.service.bill.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @className: AlipayBillDetailDO
 * @description: 支付宝账单明细
 * @author: 范小平
 * @date: 2019-09-09 14:54
 * @version: 1.0.0
 */
public class AlipayBillDetailDO {

    /**
     * pk
     */
    private Integer id;

    /**
     * 账务流水号
     */
    private String billSerialNumber;

    /**
     * 业务流水号
     */
    private String businessSerialNumber;

    /**
     * 商品订单号
     */
    private String goodsOrderNumber;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GTM+8")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;

    /**
     * 对方账号
     */
    private String payAccountNumber;

    /**
     * 收入金额
     */
    private BigDecimal inMoney;

    /**
     * 支出金额
     */
    private BigDecimal outMoney;

    /**
     * 账户余额
     */
    private BigDecimal accountBalanceMoney;

    /**
     * 交易渠道
     */
    private String dealChannel;

    /**
     * 业务类型
     */
    private String businessType;

    /**
     * 备注
     */
    private String remark;

    public AlipayBillDetailDO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillSerialNumber() {
        return billSerialNumber;
    }

    public void setBillSerialNumber(String billSerialNumber) {
        this.billSerialNumber = billSerialNumber;
    }

    public String getBusinessSerialNumber() {
        return businessSerialNumber;
    }

    public void setBusinessSerialNumber(String businessSerialNumber) {
        this.businessSerialNumber = businessSerialNumber;
    }

    public String getGoodsOrderNumber() {
        return goodsOrderNumber;
    }

    public void setGoodsOrderNumber(String goodsOrderNumber) {
        this.goodsOrderNumber = goodsOrderNumber;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getPayAccountNumber() {
        return payAccountNumber;
    }

    public void setPayAccountNumber(String payAccountNumber) {
        this.payAccountNumber = payAccountNumber;
    }

    public BigDecimal getInMoney() {
        return inMoney;
    }

    public void setInMoney(BigDecimal inMoney) {
        this.inMoney = inMoney;
    }

    public BigDecimal getOutMoney() {
        return outMoney;
    }

    public void setOutMoney(BigDecimal outMoney) {
        this.outMoney = outMoney;
    }

    public BigDecimal getAccountBalanceMoney() {
        return accountBalanceMoney;
    }

    public void setAccountBalanceMoney(BigDecimal accountBalanceMoney) {
        this.accountBalanceMoney = accountBalanceMoney;
    }

    public String getDealChannel() {
        return dealChannel;
    }

    public void setDealChannel(String dealChannel) {
        this.dealChannel = dealChannel;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}