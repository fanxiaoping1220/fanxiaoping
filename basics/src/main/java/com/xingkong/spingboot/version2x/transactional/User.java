package com.xingkong.spingboot.version2x.transactional;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * * @className: User
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/9 14:55
 **/
@Data
public class User {

    @Id
    private Long id;

    private String name;

    private String note;
}
