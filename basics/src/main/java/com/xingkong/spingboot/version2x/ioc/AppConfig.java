package com.xingkong.spingboot.version2x.ioc;

import com.xingkong.spingboot.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
@ComponentScan
public class AppConfig {

    @Bean(name = "user")
    public User initUser(){
        User user = new User();
        user.setId(1);
        user.setName("fxp");
        user.setCreateTime(LocalDateTime.now());
        return user;
    }

}
