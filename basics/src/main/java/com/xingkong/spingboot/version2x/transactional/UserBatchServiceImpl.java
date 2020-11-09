package com.xingkong.spingboot.version2x.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * * @className: UserBatchServiceImpl
 * * @description:
 * * @author: fanxiaoping
 * * @date: 2020/11/9 15:04
 **/
@Service
public class UserBatchServiceImpl implements UserBatchService {

    @Autowired
    private UserTestService userTestService;

    /**
     * 事务隔离级别
     * Isolation.DEFAULT -1 读写提交
     * Isolation.READ_UNCOMMITTED 1 未提交读
     * Isolation.READ_COMMITTED 2 读写提交
     * Isolation.REPEATABLE_READ 4 可重复读
     * Isolation.SERIALIZABLE 8 串行化
     * 事务传播性
     * Propagation.REQUIRED 需要事务，它是默认传播行为，如果当前存在事务，就沿用当前事务，否则创建一个事务运行子方法
     * Propagation.REQUIRES_NEW 无论当前事务是否存在，都会创建新事物运行方法，这样新事物就可以拥有新的锁和隔离级别等特性，与当前事务相互独立
     * Propagation.NESTED 当前方法调用子方法时，如果子方法发生异常，只回滚子方法执行过的sql，而不回滚当前的事务
     * @param list
     * @return
     */
    @Override
    //需要事务，它是默认传播行为，如果当前存在事务，就沿用当前事务，否则创建一个事务运行子方法
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int insertUsers(List<User> list) {
        int count = 0;
        for (User user : list) {
            count += userTestService.insetUser(user);
        }
        return count;
    }
}
