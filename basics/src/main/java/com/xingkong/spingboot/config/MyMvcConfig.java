package com.xingkong.spingboot.config;

import com.xingkong.spingboot.commonutil.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * * @className: MyMvcConfig
 * * @description: 自定义mvc配置
 * * @author: fan xiaoping
 * * @date: 2022/1/7 0007 17:13
 **/
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 拦截所有请求
     * 登录不拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor(redisUtil))
                //拦截所有请求
                .addPathPatterns("/**")
                //排除
                .excludePathPatterns("/rabbitmq/exchange/fanout","/error","/user/login","/content/**","/wx/**","/order/**");
    }
}
