package com.langchain4j.controller;

import com.langchain4j.entity.Reservation;
import com.langchain4j.service.ReservationService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * (Reservation)表控制层
 *
 * @author makejava
 * @since 2026-03-12 15:01:57
 */
@RestController
@RequestMapping("reservation")
public class ReservationController {
    /**
     * 服务对象
     */
    @Resource
    private ReservationService reservationService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Reservation> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.reservationService.queryById(id));
    }

    @GetMapping("/findByPhone")
    public ResponseEntity<Reservation> findByPhone(@RequestParam("phone") String phone){
        return ResponseEntity.ok(this.reservationService.findByPhone(phone));
    }

    /**
     * 新增数据
     *
     * @param reservation 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<Reservation> add(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(this.reservationService.insert(reservation));
    }

    /**
     * 编辑数据
     *
     * @param reservation 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<Reservation> edit(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(this.reservationService.update(reservation));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Long id) {
        return ResponseEntity.ok(this.reservationService.deleteById(id));
    }

}

