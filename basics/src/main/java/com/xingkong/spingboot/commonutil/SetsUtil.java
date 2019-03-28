package com.xingkong.spingboot.commonutil;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: SetsUtil
 * @Description: 泛型 set实用工具
 * @Auther: fanxiaoping
 * @Date: 2019/3/28 22:22
 * @version: 1.0.0
 */
public class SetsUtil {

    /**
     * a与b的合集
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> union(Set<T> a ,Set<T> b){
        Set<T> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }

    /**
     * b与a共有的
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> intersection(Set<T> a ,Set<T> b){
        Set<T> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }

    /**
     * 从a移除b包含的元素
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> difference(Set<T> a ,Set<T> b){
        Set<T> result = new HashSet<>(a);
        result.removeAll(b);
        return result;
    }

    /**
     * 除交集之外的元素
     * @param a
     * @param b
     * @param <T>
     * @return
     */
    public static <T> Set<T> complement(Set<T> a ,Set<T> b){
        return difference(union(a,b),intersection(a,b));
    }
}
