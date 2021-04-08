package com.an.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @program spring-practice
 * @description 
 * @author wq
 * created on 2021-04-08
 * @version  1.0.0
 */
@Configuration
public class AsynEventConfig {

    /**
     * 用主线程同步执行事件监听
     */
    //@Bean
    public SyncTaskExecutor syncTaskExecutor() {
        return new SyncTaskExecutor();
    }

    /**
     * 异步线程执行监听，不阻塞主线程方法调用
     */
    @Bean
    public ThreadPoolTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.setQueueCapacity(10);
        return executor;
    }
}
