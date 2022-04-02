package com.xingkong.spingboot;

import com.xingkong.spingboot.entity.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicsApplicationTests {

    @Resource
    DataSource dataSource;

    @Autowired
    private Person person;

    @Autowired
    private JavaMailSenderImpl mailSender;

    /**
     * 测试连接数据库
     *
     * @throws SQLException
     */
    @Test
    public void contextLoads() throws SQLException {

        System.out.println("数据源>>>>>>" + dataSource.getClass());
        Connection connection = dataSource.getConnection();

        System.out.println("连接>>>>>>>>>" + connection);
        System.out.println("连接地址>>>>>" + connection.getMetaData().getURL());
        connection.close();

    }

    @Test
    public void test(){
        System.out.println(person);
    }

    /**
     * 发送邮件
     */
    @Test
    public void sendMail(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("fan18324485857@163.com");
        simpleMailMessage.setTo("fan18324485857@163.com");
        simpleMailMessage.setSubject("发送邮件");
        simpleMailMessage.setText("你好范小平");
        mailSender.send(simpleMailMessage);
    }

}
