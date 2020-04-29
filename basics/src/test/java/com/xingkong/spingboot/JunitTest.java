package com.xingkong.spingboot;

import org.junit.*;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @className: JunitTest
 * @description:
 * @author: 范小平
 * @date: 2019-11-07 15:03
 * @version: 1.0.0
 */
@SpringBootTest
public class JunitTest {

    @BeforeClass
    public static void beforeClass() {
        System.out.println("BeforeClass");
    }

    @Before
    public void before() {
        System.out.println("Before");
    }

    @Test
    public void test() {
        System.out.println("Test");
    }

    @Test
    public void test2() {
        System.out.println("Test2");
    }

    @After
    public void after() {
        System.out.println("After");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("AfterClass");
    }
}