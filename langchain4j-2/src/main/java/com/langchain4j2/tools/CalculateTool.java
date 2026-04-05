package com.langchain4j2.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 计算工具
 */
@Component
public class CalculateTool {

    @Tool(value = "对两个数字进行加法运算")
    public Double add(Double a, Double b){
        return new BigDecimal(a).add(new BigDecimal(b)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Tool(value = "对两个数字进行减法运算")
    public Double sub(Double a, Double b){
        return new BigDecimal(a).subtract(new BigDecimal(b)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Tool(value = "对两个数字进行乘法运算")
    public Double multiply(Double a, Double b){
        return new BigDecimal(a).multiply(new BigDecimal(b)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Tool(value = "对两个数字进行除法运算")
    public Double divide(Double a, Double b){
        return new BigDecimal(a).divide(new BigDecimal(b)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
