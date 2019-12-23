package com.alison.spring.javaConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author alison
 * @Date 2019/10/8  22:24
 * @Version 1.0
 * @Description
 */
@Configuration
public class AppConfig {
    @Bean(name="entitlement")
    public Entitlement entitlement() {
        Entitlement ent= new Entitlement();
        ent.setName("Entitlement");
        ent.setTime(1);
        return ent;
    }

    @Bean(name="entitlement2")
    public Entitlement entitlement2() {
        Entitlement ent= new Entitlement();
        ent.setName("Entitlement2");
        ent.setTime(2);
        return ent;
    }
}