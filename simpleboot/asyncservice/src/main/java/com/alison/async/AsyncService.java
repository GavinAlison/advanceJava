package com.alison.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author alison
 * @Date 2019/12/11  22:07
 * @Version 1.0
 * @Description
 */
@Service
public class AsyncService {
    @Async
    public void withException() {
        throw new RuntimeException("出错了");
    }
}
