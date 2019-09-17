package com.xingkong.spingboot.service.bill.dao;

import com.xingkong.spingboot.service.bill.entity.AlipayBillDetailDO;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
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

    /**
     * 查询多少天以内的 + 排序规则
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param orderRole 排序规则
     * @return
     */
    @Results({
            @Result(column = "bill_serial_number",property = "billSerialNumber"),@Result(column = "business_serial_number",property = "businessSerialNumber"),@Result(column = "goods_order_number",property = "goodsOrderNumber"),
            @Result(column = "goods_name",property = "goodsName"),@Result(column = "create_time",property = "createTime"),@Result(column = "pay_account_number",property = "payAccountNumber"),
            @Result(column = "in_money",property = "inMoney"),@Result(column = "out_money",property = "outMoney"),@Result(column = "accountBalanceMoney",property = "accountBalanceMoney"),
            @Result(column = "deal_channel",property = "dealChannel"),@Result(column = "business_type",property = "businessType")
    })
    @Select("SELECT * FROM alipay_bill_detail where create_time >= #{startTime} and create_time <= #{endTime} ORDER BY create_time ${orderRole}")
    List<AlipayBillDetailDO> getList(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime, @Param("orderRole") String orderRole);
}
