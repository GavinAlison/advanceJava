package com.alison;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @Classname Config
 * @Date 2021/9/4 16:27
 * @Created by alison
 */
@Configuration
public class Config {

    @Lazy// 减少springIOC容器启动的加载时间
    @Bean
    public Person person(){
        return new Person();
    }

    @Bean(name = "initiBean", initMethod = "init", destroyMethod = "destroyMethod")
    public InitiAndDestroyBean initiBean(){
        return new InitiAndDestroyBean();
    }
}
