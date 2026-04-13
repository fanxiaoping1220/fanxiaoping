package com.xingkong.spingboot.config;

import com.xingkong.spingboot.commonutil.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * * @className: LoginHandlerInterceptor
 * * @description: 登录拦截器
 * * @author: fan xiaoping
 * * @date: 2022/1/7 0007 17:01
 **/
public class LoginHandlerInterceptor implements HandlerInterceptor {

    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("USER_ID");
        if(StringUtils.isBlank(userId)){
            response.sendError(401,"没有权限, 请先登录!");
            return false;
        }
        if(!redisUtil.hasKey(userId)){
            response.sendError(401,"没有权限, 请先登录!");
            return false;
        }
        return true;
    }

    public LoginHandlerInterceptor(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
