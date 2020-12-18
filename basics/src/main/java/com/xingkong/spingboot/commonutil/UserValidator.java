package com.xingkong.spingboot.commonutil;

import com.xingkong.spingboot.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * * @className: UserValidator
 * * @description: 自定义验证器
 * * @author: fanxiaoping
 * * @date: 2020/12/18 14:26
 **/
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //对象为空
        if(target == null){
            errors.rejectValue("",null,"用户不能为空");
            return;
        }
        User user = (User) target;
        if(StringUtils.isBlank(user.getName())){
            errors.rejectValue("name",null,"用户名不能为空");
        }
    }
}
