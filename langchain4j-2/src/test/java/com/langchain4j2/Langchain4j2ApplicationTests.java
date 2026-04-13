package com.langchain4j2;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.langchain4j2.dao.TPADEDDao;
import com.langchain4j2.entity.slave.TPADED;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Langchain4j2ApplicationTests {

    @Autowired
    private TPADEDDao tpadedDao;

    @Test
    void contextLoads() {
    }

    @DS("slave")
    @Test
    void testSqlServer(){
        // 先查询一条数据看看日期字段的实际格式
        List<TPADED> list = tpadedDao.selectList(new LambdaQueryWrapper<TPADED>().eq(TPADED::getDED001,"0"));
        System.out.println("Total records: " + list.size());
        Page<TPADED> page = tpadedDao.selectPage(Page.of(1, 10), new LambdaQueryWrapper<TPADED>().eq(TPADED::getDED001,"0"));
        System.out.println(page);
    }

}
