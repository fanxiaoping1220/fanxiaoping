package com.xingkong.spingboot.service.bill.dao;

import com.xingkong.spingboot.service.bill.entity.AlipayBillDetailDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @className: AlipayBillDetailDAO
 * @description: 支付宝账单明细sql
 * @author: 范小平
 * @date: 2019-09-10 10:31
 * @version: 1.0.0
 */
@Mapper
public interface AlipayBillDetailDAO {

    /**
     * 批量插入
     * @param list
     * @return
     */
    @Insert("<script>" +
            "insert into alipay_bill_detail " +
            "(bill_serial_number,business_serial_number,goods_order_number,goods_name,create_time,pay_account_number,in_money,out_money,account_balance_money,deal_channel,business_type,remark)" +
            "values" +
            "<foreach item = 'item' collection = 'list' separator = ','>" +
            "(#{item.billSerialNumber},#{item.businessSerialNumber},#{item.goodsOrderNumber},#{item.goodsName},#{item.createTime},#{item.payAccountNumber},#{item.inMoney},#{item.outMoney},#{item.accountBalanceMoney},#{item.dealChannel},#{item.businessType},#{item.remark})" +
            "</foreach>" +
            "</script>")
    int branchInsert(List<AlipayBillDetailDO> list);
}
