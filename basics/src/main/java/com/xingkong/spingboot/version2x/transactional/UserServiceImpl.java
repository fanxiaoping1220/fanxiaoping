package com.xingkong.spingboot.version2x.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * * @className: UsreServiceImpl
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/17 17:04
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Cacheable(value = "redisCache",key = "'redis_user'+#id")
    public User getById(Long id) {
        User user = userDao.getById(id);
        return user;
    }

    @Override
    @CachePut(value = "redisCache",key = "'redis_user'+#result.id")
    public User add(User user) {
        userDao.insert(user);
        return user;
    }

    /**
     * 删除完成之后再删除缓存
     * @param id
     * @return
     */
    @Override
    @CacheEvict(value = "redisCache",key = "'redis_user'+#id",beforeInvocation = false)
    public int deleteById(Long id) {
        userDao.deleteById(id);
        return 0;
    }

    @Override
    @CachePut(value = "redisCache",condition = "#result != 'null '",key = "'redis_user'+#result.id")
    public User updateById(User user) {
        User users = userDao.getById(user.getId());
        if(users == null){
            return null;
        }
        userDao.update(user);
        return user;
    }
}
