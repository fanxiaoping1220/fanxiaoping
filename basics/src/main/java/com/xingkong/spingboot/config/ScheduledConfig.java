package com.xingkong.spingboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @className: ScheduledConfig
 * @description: 定时任务配置
 * @author: 范小平
 * @date: 2019-11-01 10:53
 * @version: 1.0.0
 */
@Configuration
@EnableAsync
public class ScheduledConfig {

    /**
     * 每一个定时任务每次创建一个新的线程
     * 创建一个定时任务的线程池size为100
     *
     * @return
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(100);
        return taskScheduler;
    }

}