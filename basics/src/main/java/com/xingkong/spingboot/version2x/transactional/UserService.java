package com.xingkong.spingboot.version2x.transactional;

/**
 * * @className: UsreService
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/17 17:04
 **/
public interface UserService {

    /**
     * 获取当个用户
     * @param id
     * @return
     */
    User getById(Long id);

    /**
     * 添加
     * @param user
     * @return
     */
    User add(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 更新用户
     * @param user
     * @return
     */
    User updateById(User user);
}
