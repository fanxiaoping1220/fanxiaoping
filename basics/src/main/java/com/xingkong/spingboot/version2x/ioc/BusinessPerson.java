package com.xingkong.spingboot.version2x.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * * @className: BussinessPerson
 * * @description: 人类实现类
 * * @author: fanxiaoping
 * * @date: 2020/10/26 17:46
 **/
@Service
public class BusinessPerson implements Person {

    @Autowired
    //选择用哪个 cat，还是dog
    @Qualifier("dog")
    private Animal animal;

    @Override
    public void service() {
        this.animal.use();
    }

    @Override
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
