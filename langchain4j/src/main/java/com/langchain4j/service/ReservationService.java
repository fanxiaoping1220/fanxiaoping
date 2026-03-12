package com.langchain4j.service;

import com.langchain4j.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (Reservation)表服务接口
 *
 * @author makejava
 * @since 2026-03-12 15:01:58
 */
public interface ReservationService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Reservation queryById(Long id);

    /**
     * 新增数据
     *
     * @param reservation 实例对象
     * @return 实例对象
     */
    Reservation insert(Reservation reservation);

    /**
     * 修改数据
     *
     * @param reservation 实例对象
     * @return 实例对象
     */
    Reservation update(Reservation reservation);

    /**
     * 通过phone查询
     *
     * @param phone
     * @return
     */
    Reservation findByPhone(String phone);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
