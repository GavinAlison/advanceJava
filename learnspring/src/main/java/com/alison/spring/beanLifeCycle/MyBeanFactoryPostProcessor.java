package com.alison.spring.beanLifeCycle;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyBeanFactoryPostProcessor() {
        super();
        System.out.println("MyBeanFactoryPostProcessor-->2. 初始化BeanFactoryPostProcessor构造器");
    }


    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor-->2.1. 调用BeanFactoryPostProcessor的postProcessBeanFactory方法");
//        BeanDefinition bd = arg0.getBeanDefinition("person");
//        bd.getPropertyValues().addPropertyValue("phone", "110");
    }


}