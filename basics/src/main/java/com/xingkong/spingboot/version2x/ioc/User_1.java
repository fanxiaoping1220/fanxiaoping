package com.xingkong.spingboot.version2x.ioc;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class User_1 {

    @Value("1")
    private Integer id;

    @Value("fxp_user_1")
    private String name;

}
