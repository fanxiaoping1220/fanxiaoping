package com.xingkong.springboot.user.service.impl;

import com.xingkong.springboot.user.entity.User;
import com.xingkong.springboot.user.mapper.UserMapper;
import com.xingkong.springboot.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fanxiaoping
 * @since 2023-11-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
