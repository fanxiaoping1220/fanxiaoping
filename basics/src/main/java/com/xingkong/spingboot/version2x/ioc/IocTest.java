package com.xingkong.spingboot.version2x.ioc;

import com.xingkong.spingboot.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Slf4j
public class IocTest {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        User user = ctx.getBean(User.class);
        log.info(user.getName());
        User_1 user_1 = ctx.getBean(User_1.class);
        log.info(user_1.getName());
        BusinessPerson person = ctx.getBean(BusinessPerson.class);
        person.service();
    }
}
