package com.alison;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Classname InitiBean
 * @Date 2021/9/4 17:28
 * @Created by alison
 */
//@Component
public class InitiAndDestroyBean implements InitializingBean, DisposableBean {

    public InitiAndDestroyBean() {//0
        System.out.println("constructor....0");
    }

    @Override
    public void afterPropertiesSet() throws Exception {//2
        System.out.println("InitializingBean...afterPropertiesSet...2");
    }

    public void init() {//3
        System.out.println("init-method-----init----3");
    }

    @PostConstruct
    public void PostConstruct() {//1
        System.out.println("PostConstruct-----init-----1");
    }

    @Override
    public void destroy() throws Exception {// 2
        System.out.println("DisposableBean---- destroy");
    }

    @PreDestroy
    public void preDestroy() {//0
        System.out.println("preDestroy...0");
    }

    public void destroyMethod() {// 3
        System.out.println("destroyMethod...");
    }

}
