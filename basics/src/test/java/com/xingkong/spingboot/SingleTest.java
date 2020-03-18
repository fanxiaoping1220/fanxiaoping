package com.xingkong.spingboot;

import com.xingkong.spingboot.designMode.SingleMode;
import com.xingkong.spingboot.designMode.SingleMode2;
import com.xingkong.spingboot.designMode.SingleMode3;
import com.xingkong.spingboot.designMode.SingleMode4;
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

    /**======================================================饿汉模式测试=========================================================================================================*/

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

    /**======================================================静态内部类模式测试（登记式）=========================================================================================================*/

    @Test
    public void test4(){
        SingleMode3 s1 = SingleMode3.getInstance();
        SingleMode3 s2 = SingleMode3.getInstance();
        System.out.println(s1==s2);
    }

    /**
     * 反射的测试（有问题）
     * 防止通过用反射的形式创建对象照成的攻击，解决方案{@link SingleMode3 的SingleMode3()}
     * @throws Exception
     */
    @Test
    public void test5() throws Exception{
        Class clazz = SingleMode3.class;
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleMode3 s1 = SingleMode3.getInstance();
        SingleMode3 s2 = (SingleMode3) constructor.newInstance();
        System.out.println(s1==s2);//false
    }

    /**======================================================枚举模式测试=========================================================================================================*/

    @Test
    public void test6(){
        SingleMode4 s1 = SingleMode4.INSTANCE;
        SingleMode4 s2 = SingleMode4.INSTANCE;
        System.out.println(s1==s2);
    }

    /**
     * 能有效的防止反射的形式创建的对象照成的攻击
     * @throws Exception
     */
    @Test
    public void test7()throws Exception{
        Class clazz = SingleMode4.class;
        Constructor constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        SingleMode4 s1 = SingleMode4.INSTANCE;
        SingleMode4 s2 = (SingleMode4) constructor.newInstance();
        System.out.println(s1==s2);
    }

    /**======================================================懒汉模式测试=========================================================================================================*/

    @Test
    public void test8(){
        SingleMode2 s1 = SingleMode2.getInstance();
        SingleMode2 s2 = SingleMode2.getInstance();
        System.out.println(s1==s2);
    }

    /**
     * 多个线程的方式测试
     * 需要采用加锁的形式synchronized{@link SingleMode2}
     */
    @Test
    public void test9(){
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(50,Integer.MAX_VALUE,0,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(SingleMode2.getInstance());
            });
        }
        threadPoolExecutor.shutdown();
    }


}
