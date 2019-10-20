package com.xingkong.spingboot.controller.bill.dto;

/**
 * @className: AlipayBillDetailDO
 * @description: 支付宝账单明细
 * @author: 范小平
 * @date: 2019-09-09 14:54
 * @version: 1.0.0
 */
public class AlipayBillDetailDTO {

    /**
     * 几天之内
     */
    private Integer number;

    /**
     * 排序规则 asc desc
     */
    private String orderRole;

    public AlipayBillDetailDTO() {
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getOrderRole() {
        return orderRole;
    }

    public void setOrderRole(String orderRole) {
        this.orderRole = orderRole;
    }
}