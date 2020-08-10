package com.alison.spring.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.dmz.source.code")
public class Config {
    @Bean
    public A a() {
        return new A(b());
    }

    @Bean
    public B b() {
        return new B();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        // 会内置一些beanProcess后置处理器
//        internalConfigurationAnnotationProcessor
//        internalEventListenerProcessor
//        internalEventListenerFactory
//        internalAutowiredAnnotationProcessor
//        internalCommonAnnotationProcessor

//        create B
//        create A
//        Creating shared instance of singleton bean 'b'
//        create B
    }
}


//@Component
class B {
    public B() {
        System.out.println("create B");
    }
}

class A {
    public A(B b) {
        System.out.println("create A ");
    }
}
