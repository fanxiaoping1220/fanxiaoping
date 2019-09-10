package com.xingkong.spingboot.service.bill.dao;

import com.xingkong.spingboot.service.bill.entity.AlipayBillTotalDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @className: AlipayBillTotalDAO
 * @description: 每日账单汇总sql
 * @author: 范小平
 * @date: 2019-09-10 10:41
 * @version: 1.0.0
 */
@Mapper
public interface AlipayBillTotalDAO {

    /**
     * 批量插入
     * @param list
     * @return
     */
    @Insert("<script>" +
            "insert into alipay_bill_total " +
            "(type,in_number,in_money,out_number,out_money,total_money,create_time)" +
            "values" +
            "<foreach item = 'item' collection = 'list' separator = ','>" +
            "(#{item.type},#{item.inNumber},#{item.inMoney},#{item.outNumber},#{item.outMoney},#{item.totalMoney},#{item.createTime})" +
            "</foreach>" +
            "</script>")
    int brnanchInsert(List<AlipayBillTotalDO> list);
}
