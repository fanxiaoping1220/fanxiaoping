package com.langchain4j2.tools;

import com.langchain4j2.entity.ProductInfo;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品库存工具
 */
@Component
public class ProductTool {

    public static final Map<String, ProductInfo> productInfoMap = new HashMap<>();

    static {
        productInfoMap.put("iphone17",new ProductInfo("iphone17",true,"3-5天配送"));
        productInfoMap.put("macbook air",new ProductInfo("macbook air",false,"2-3天配送"));
        productInfoMap.put("苹果耳机",new ProductInfo("苹果耳机",true,"3-5天配送"));
    }

    @Tool(value = "检查商品库存是否充足")
    public String checkStock(String productName){
        if(!productInfoMap.containsKey(productName)){
            return "抱歉，没有找到"+ productName + "相关信息";
        }
        ProductInfo productInfo = productInfoMap.get(productName);
        return productInfo.getIsStock()?productInfo.getProductName()+"目前库存充足，可以正常下单"
                : productInfo.getProductName()+"目前库存不足，我们会尽快补货的";
    }

    @Tool(value = "获取商品配送时间")
    public String getDeliveryTime(String productName){
        if(!productInfoMap.containsKey(productName)){
            return "抱歉，没有找到"+ productName + "相关信息";
        }
        return "您查询的商品" + productName + "的配送时间为:" +productInfoMap.get(productName).getDeliveryTime();
    }
}
