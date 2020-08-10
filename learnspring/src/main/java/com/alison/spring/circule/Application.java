package com.alison.spring.circule;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
        annotationConfigApplicationContext.register(A.class);
        annotationConfigApplicationContext.register(B.class);
        annotationConfigApplicationContext.refresh();
        annotationConfigApplicationContext.getBean(A.class);
        annotationConfigApplicationContext.close();
    }
}
