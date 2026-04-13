package com.xingkong.spingboot.controller.excel.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * * @className: ReadUser
 * * @description: 读取excel 导入用户
 * * @author: fanxiaoping
 * * @date: 2021/4/26 17:05
 **/
@Slf4j
public class ReadUser extends AnalysisEventListener<UserInfoDTO> {

    /**
     * 每100存储一次，实际使用中可以3000，然后清理list 方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    List<UserInfoDTO> list = new ArrayList<>();

    @Override
    public void invoke(UserInfoDTO data, AnalysisContext context) {
        log.info("解析到第一条数据:{}", JSON.toJSON(data));
        list.add(data);
        if(list.size() >= BATCH_COUNT){
            saveData(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData(list);
        log.info("所有数据解析完成！");
    }

    /**
     * 保存数据到数据库
     * @param list
     */
    private void saveData(List<UserInfoDTO> list){
        log.info("{"+list.size()+"},开始存储数据库");
        log.info("存储数据库成功");
    }
}
