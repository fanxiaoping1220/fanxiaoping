package com.xingkong.spingboot.version2x.transactional;

import java.util.List;

/**
 * * @className: UserBathchService
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/9 15:04
 **/
public interface UserBatchService {

    int insertUsers(List<User> list);
}
