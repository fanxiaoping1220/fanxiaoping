package com.xingkong.spingboot.test;

import com.xingkong.spingboot.commonutil.SetsUtil;
import com.xingkong.spingboot.enums.Watercolors;

import java.util.EnumSet;
import java.util.Set;

/**
 * @ClassName: WatercolorSets
 * @Description: 测试泛型util
 * @Auther: fanxiaoping
 * @Date: 2019/3/28 22:40
 * @version: 1.0.0
 */
public class WatercolorSets {

    public static void main(String[] args) {
        Set<Watercolors> set1 = EnumSet.range(Watercolors.A, Watercolors.H);
        Set<Watercolors> set2 = EnumSet.range(Watercolors.A1, Watercolors.H1);
        Set<Watercolors> set3 = EnumSet.range(Watercolors.A2, Watercolors.H2);
        System.out.print(set1);
        System.out.print(set2);
        System.out.print(set3);
        System.out.println();
        System.out.println("并集union(set1,set2):"+SetsUtil.union(set1,set2));
        System.out.println("共有intersection(set1,set2):"+SetsUtil.intersection(set1,SetsUtil.union(set1,set2)));
        System.out.println("从a移除b包含的元素difference(set1,set2):"+SetsUtil.difference(SetsUtil.union(set1,set2),set1));
        System.out.println("除交集之外的元素(set1,set2):"+SetsUtil.complement(set1,SetsUtil.union(set1,set2)));
    }
}
