package com.alison.spring.spconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration // 将类注入到Spring容器中
@EnableAsync //开启SpringBoot异步调用（针对异步调用的方法，开启新线程），在启动类上使用也可以
public class AsyncConfig {

    @Bean(name = "jobSyncExecutor")
    public ThreadPoolTaskExecutor getScorePoolTaskExecutor() {
        // 创建线程池对象
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(10);
        //线程池维护线程的最大数量,只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(100);
        //缓存队列
        taskExecutor.setQueueCapacity(50);
        //许的空闲时间,当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        taskExecutor.setKeepAliveSeconds(200);
        //.异步方法内部线程名称
        taskExecutor.setThreadNamePrefix("jobSyncExecute-");
        /**
         0         * 当线程池的任务缓存队列已满，并且线程池中的线程数目达到maximumPoolSize,如果还有任务到来就会采取任务拒绝策略
         1         * 通常有以下四种策略:
         2         * ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecuti onException异常。
         3         * ThreadPoolExecutor.DiscardPolicy: 也是丢弃任务，但是不抛出异常。
         4         * ThreadPoolExecutor. DiscardOldestPolicy: 丢弃队列最前面的任务，然后重新尝试执行任务(重复此过程)
         5         * ThreadPoolExecutor . CallerRunsPolicy:重试添加当前的任务，自动重复调用execute()方法，直到成功
         6         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }
}
