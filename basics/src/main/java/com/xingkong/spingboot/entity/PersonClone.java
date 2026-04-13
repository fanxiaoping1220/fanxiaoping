package com.xingkong.spingboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @className: PersonClone
 * @author: fanxiaoping
 * @date: 2025/5/5
 * @description: 测试浅拷贝与深拷贝
 */
@Slf4j
@Accessors(chain = true)
@Data
@AllArgsConstructor
public class PersonClone implements Cloneable {

    /**
     * 姓名
     */
    private String name;

    /**
     * 地址
     */
    private Address address;

    /**
     * 没有重写clone则属于浅拷贝,则改变address的province,则personClone的address中的province也会改变
     * 有重写clone则属于深拷贝,则改变address的province,则personClone的address中的province不会改变
     * 浅拷贝: 源对象--->引用类型属性<----拷贝对象
     * 深拷贝: 源对象--->引用类型属性--->拷贝对象 对基本数据类型进行值传递,为数据类型创建一个新的对象，并复制其内容
     * @return
     */
    @Override
    public Object clone() {
        try {
            PersonClone clone = (PersonClone) super.clone();
            clone.setAddress(address.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    @Data
    static class Address implements Cloneable{
        /**
         * 省份
         */
        private String province;

        /**
         * 没有重写clone则属于浅拷贝,则改变address的province,则personClone的address中的province也会改变
         * 有重写clone则属于深拷贝,则改变address的province,则personClone的address中的province不会改变
         * @return
         */
        @Override
        public Address clone() throws CloneNotSupportedException {
            return (Address) super.clone();
        }


    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Address address = new Address();
        address.setProvince("北京");
        PersonClone personClone = new PersonClone("范小平",address);
        PersonClone personClone1 = (PersonClone) personClone.clone();
        personClone1.getAddress().setProvince("浙江");
        log.info("personClone:{}",personClone);
        log.info("personClone1:{}",personClone1);
    }
}
