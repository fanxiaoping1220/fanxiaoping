package com.xingkong.spingboot.version2x.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * * @className: UserServiceImpl
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/9 14:58
 **/
@Service
public class UserTestServiceImpl implements UserTestService {

    @Autowired
    private UserDao userDao;

    @Override
    //无论当前事务是否存在，都会创建新事物运行方法，这样新事物就可以拥有新的锁和隔离级别等特性，与当前事务相互独立
//    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW)
    //当前方法调用子方法时，如果子方法发生异常，只回滚子方法执行过的sql，而不回滚当前的事务
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.NESTED)
    public int insetUser(User user) {
        int i = userDao.insert(user);
        return i;
    }
}
