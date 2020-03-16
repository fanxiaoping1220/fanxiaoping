package com.xingkong.spingboot;

import com.xingkong.spingboot.designMode.SingleMode;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author fanxiaoping
 * @className: SingleTest
 * @description: 单例模式测试
 * @date 2020-03-16 23:29:43
 */
@SpringBootTest
public class SingleTest {

    /**
     * 获取的同一个对象
     */
    @Test
    public void  test(){
        SingleMode s1 = SingleMode.getInstance();
        SingleMode s2 = SingleMode.getInstance();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1==s2);
    }

    /**
     * 采用线程的方式测试
     */
    @Test
    public void test2(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50,Integer.MAX_VALUE,0, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(SingleMode.getInstance());
            });
        }
        threadPoolExecutor.shutdown();
    }

    /**
     * 反射的测试
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        Class clazz = SingleMode.class;
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleMode s1 = SingleMode.getInstance();
        SingleMode s2 = (SingleMode) constructor.newInstance();
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s1==s2);//false
    }

}
