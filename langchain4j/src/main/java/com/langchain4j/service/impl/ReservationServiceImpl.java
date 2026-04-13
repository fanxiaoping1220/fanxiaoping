package com.langchain4j.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.langchain4j.dao.ReservationDao;
import com.langchain4j.entity.Reservation;
import com.langchain4j.service.ReservationService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * (Reservation)表服务实现类
 *
 * @author makejava
 * @since 2026-03-12 15:01:58
 */
@Service("reservationService")
public class ReservationServiceImpl implements ReservationService {
    @Resource
    private ReservationDao reservationDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Reservation queryById(Long id) {
        return this.reservationDao.selectById(id);
    }

    /**
     * 新增数据
     *
     * @param reservation 实例对象
     * @return 实例对象
     */
    @Override
    public Reservation insert(Reservation reservation) {
        this.reservationDao.insert(reservation);
        return reservation;
    }

    /**
     * 修改数据
     *
     * @param reservation 实例对象
     * @return 实例对象
     */
    @Override
    public Reservation update(Reservation reservation) {
        this.reservationDao.updateById(reservation);
        return this.queryById(reservation.getId());
    }

    @Override
    public Reservation findByPhone(String phone) {
        return reservationDao.selectOne(new LambdaQueryWrapper<Reservation>().eq(Reservation::getPhone, phone));
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.reservationDao.deleteById(id) > 0;
    }
}
