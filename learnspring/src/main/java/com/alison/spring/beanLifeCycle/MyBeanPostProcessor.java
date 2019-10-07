package com.alison.spring.beanLifeCycle;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {

    public MyBeanPostProcessor() {
        super();
        System.out.println("MyBeanPostProcessor-->2.2. 调用BeanPostProcessor构造器");
    }


    public Object postProcessBeforeInitialization(Object arg0, String arg1)throws BeansException {
        System.out.println("MyBeanPostProcessor-->3.7. 调用BeanPostProcessor的postProcessBeforeInitialization方法");
        return arg0;
    }

    public Object postProcessAfterInitialization(Object arg0, String arg1)throws BeansException {
        System.out.println("MyBeanPostProcessor-->3.11.  BeanPostProcessor接口方法postProcessAfterInitialization对属性进行更改！");
        return arg0;
    }
}