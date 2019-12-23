package com.alison.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @Author alison
 * @Date 2019/12/11  20:55
 * @Version 1.0
 * @Description
 */

@SpringBootApplication
public class AsyncApplication {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SpringApplication.run(AsyncApplication.class, args);
        AsyncApplication asyncApplication = new AsyncApplication();
        asyncApplication.asyncMethodWithVoidReturnType();
        Future<String> future = asyncApplication.asyncMethodWithReturnType();
        System.out.println("application starting....");
        while (true) {  //这里使用了循环判断，等待获取结果信息
            if (future.isDone()) {  //判断是否执行完毕
                System.out.println("Result from asynchronous process - " + future.get());
                break;
            }
            System.out.println("Continue doing something else. ");
            Thread.sleep(10 * 1000);
        }
        asyncApplication.syncTask();
    }

    @Async  //标注使用 --基于@Async无返回值调用
    public void asyncMethodWithVoidReturnType() {
        System.out.println("Execute method asynchronously. " + Thread.currentThread().getName());
    }

    @Async // 有返回值 Future
    public Future<String> asyncMethodWithReturnType() {
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
            return new AsyncResult<String>("hello world !!!!");
        } catch (InterruptedException e) {
            //
        }
        return null;
    }

    @Async
    public void syncTask() {
        String str = null;
        str.split(",");
    }

    @Resource
    AsyncService asyncService;

    public void test() {
        asyncService.withException();
    }

}
