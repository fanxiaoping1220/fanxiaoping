package com.xingkong.spingboot.controller.redis;

import com.xingkong.spingboot.commonutil.RedisUtil;
import com.xingkong.spingboot.entity.Product;
import com.xingkong.spingboot.entity.UserRedis;
import com.xingkong.spingboot.service.UserRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.xingkong.spingboot.service.impl.JHSTaskServiceImpl.*;

/**
 * * @className: UserRedisController
 * * @description:
 * * @author: fan xiaoping
 * * @date: 2023/11/17 0017 17:07
 **/
@Slf4j
@RequestMapping(value = "/redis/user")
@RestController
public class UserRedisController {

    @Autowired
    private UserRedisService userRedisService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 添加
     * @param user
     */
    @PostMapping(value = "/addUser")
    public void addUser(@RequestBody UserRedis user){
         userRedisService.addUser(user);
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @GetMapping(value = "/getUser/{id}")
    public UserRedis getUser(@PathVariable(name = "id") Long id){
        return userRedisService.getUser(id);
    }

    /**
     * 从BloomFiler --> redis --> mysql
     * 校验改用户是否在白名单中，不存在则返回null
     * @param customerId
     * @return
     */
    @GetMapping(value = "/customer/{customerId}")
    public UserRedis findCustomerByIdWithBloomFilter(@PathVariable(name = "customerId") Integer customerId){
        return userRedisService.findCustomerByIdWithBloomFilter(customerId);
    }

    /**
     * google guava 布隆过滤器 case2
     */
    @PostMapping(value = "/google/guavaBloomFilter")
    public void guavaBloomFilter(){
        userRedisService.guavaBloomFilter();
    }

    /**
     * 获取下一个视频,已推荐过的不推荐
     * @return
     */
    @GetMapping(value = "/getVideo")
    public Integer getVideo(){
        return userRedisService.getVideo();
    }

    /**
     * 分页查询:在高并发的情况下，只能走redis查询，走db的话必定会把db打垮
     * 聚划算案例，每次1页每页5条显示
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/product/find")
    public List<Product> find(@RequestParam(name = "page") int page,@RequestParam(name = "size") int size){
        List<Product> list = new ArrayList<>();
        long start = (page - 1) * size;
        long end = start + size -1;
        try {
            list = (List<Product>) redisUtil.lGet(JHS_KEY, start, end);
            if(list.isEmpty()){
                //TODO 走mysql查询
            }
            log.info("参加活动的商家:{}",list);
        }catch (Exception e){
            log.error("jhs exception:{}",e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * AB双缓存架构，防止热点key突然失效
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/product/findAB")
    public List<Product> findAB(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        List<Product> list = new ArrayList<>();
        long start = (page - 1) * size;
        long end = start + size - 1 ;
        try {
            list = (List<Product>) redisUtil.lGet(JHS_KEY_A,start,end);
            if(list.isEmpty()){
                log.info("--A缓存已经过期失效或者活动结束了，记得人工修改，B缓存继续顶着");
                list = (List<Product>) redisUtil.lGet(JHS_KEY_B,start,end);
                if(list.isEmpty()){
                    //TODO 走mysql查询
                }
            }
            log.info("参加活动的商家为:{}",list);
        }catch (Exception e){
            log.error("jhs exception:{}",e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * lock锁
     * 库存扣减,一次卖一个
     * @return
     */
    @GetMapping(value = "/inventory/sale")
    public String sale(){
        return userRedisService.sale();
    }

    /**
     * redis锁
     * 库存扣减,一次卖-个
     * @return
     */
    @GetMapping(value = "/inventory/sale2")
    public String sale2(){
        return userRedisService.sale2();
    }
}
