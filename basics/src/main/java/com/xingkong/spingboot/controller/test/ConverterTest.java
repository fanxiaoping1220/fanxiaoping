package com.xingkong.spingboot.controller.test;

import com.xingkong.spingboot.commonutil.UserValidator;
import com.xingkong.spingboot.entity.User;
import com.xingkong.spingboot.entity.ValidatorPojo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * * @className: ConverterTest
 * * @description: 转换器测试
 * * @author: fanxiaoping
 * * @date: 2020/12/17 10:46
 **/
@RequestMapping(value = "/converter")
@RestController
public class ConverterTest {

    /**
     * 数据
     * 1-user-2020/01/17 10:49:12
     * @param user
     * @return
     */
    @GetMapping(value = "/getUserByConverter")
    public User getUserByConverter(User user){
        return user;
    }

    /**
     * 数据
     * 1-user-2020/01/17 10:49:12,2-user-2020/12/17 10:49:12,3-user-2020/02/17 10:49:12
     * StringToCollectionConverter通过逗号分隔-->然后到StringToUserConverter-->最后到getList
     * @param userList
     * @return
     */
    @GetMapping(value = "/getList")
    public List<User> getList(List<User> userList){
        return userList;
    }

    @PostMapping(value = "/validTest")
    public Map<String,Object> validTest(@Valid @RequestBody ValidatorPojo pojo){
        return null;
    }

    @PostMapping(value = "/validUserTest")
    public Map<String,Object> validUserTest(@Valid @RequestBody User user){
        return null;
    }

    /**
     * 调用控制器前先执行这个方法
     * 这里的initBinder方法因为标注注解@inteBinder,因为会在控制器方法前被执行，并且将WebDataBinder对象传递进去，
     * 在这个方法里绑定了自定义的验证器U{@link UserValidator},而且设置了日期的格式，所以在控制器中不再需要使用@DateTimeFormat去定义日期格式化。
     * 通过这样的自定义，在使用注解@Vaild标注User参数后，spring MVC就会去遍历对应的验证器，当遍历到{@link UserValidator}时，会去执行它的supports方法。
     * 因为该方法会返回true，所以spring MVC会用这个验证器去验证user类的数据。对于日期类型只指定了对应的格式，这样控制器的Date类型的参数也不需要在使用注解的协作。
     * @param binder
     */
    @InitBinder
    public void initBinder(WebDataBinder binder){
        //绑定验证器
        binder.setValidator(new UserValidator());
        //定义日期参数格式，参数不在需要注解@DateTimeFormat, boolean参数表示是否允许为空
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),false));
    }
}
