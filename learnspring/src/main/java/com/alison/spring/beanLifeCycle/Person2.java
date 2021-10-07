package com.alison.spring.beanLifeCycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.stereotype.Component;

public class Person2 implements BeanFactoryAware, BeanNameAware,
        InitializingBean, DisposableBean {

    private String name;
    private String address;
    private int phone;

    private BeanFactory beanFactory;
    private String beanName;

    public Person2() {
        System.out.println("Person2-->3.2. 调用Person的构造器");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("Person2-->【注入属性】注入属性name");
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        System.out.println("Person2-->【注入属性】注入属性address");
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        System.out.println("Person2-->【注入属性】注入属性phone");
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person-->Person2 [address=" + address + ", name=" + name + ", phone="
                + phone + "]";
    }

    public void setBeanName(String arg0) {
        System.out.println("Person2-->3.5. 调用BeanNameAware.setBeanName方法--->" + arg0);
        this.beanName = arg0;
    }

    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        System.out.println("Person2-->3.6. 调用BeanFactoryAware.setBeanFactory方法--->beanFactory: " + arg0);
        this.beanFactory = arg0;
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("Person2-->3.9. 调用InitializingBean的afterPropertiesSet方法");
    }

    public void destroy() throws Exception {
        System.out.println("Person2-->调用DiposibleBean的destory方法");
    }

    public void myInit() {
        System.out.println("Person2-->3.10. 调用<bean>的init-method属性指定的初始化方法");
    }

    public void myDestory() {
        System.out.println("Person2-->调用<bean>的destroy-method属性指定的初始化方法");
    }
}
