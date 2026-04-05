package com.langchain4j2.tools;

import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试工具类
 */
@Slf4j
@Component
public class TestTool {

    @Tool(value = "获取用户所在班级")
    public String getUserClass(String name){
        log.info("调用工具类:{},name={}",TestTool.class,name);
        return "一班";
    }

    @Tool(value = "根据地址，获取天气预报，获取当前的天气情况")
    public String getWeather(String address){
        log.info("调用工具类:{},address={}",TestTool.class,address);
        return "今天天气晴朗";
    }
}
