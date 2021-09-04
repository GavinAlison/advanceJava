package com.alison;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Classname AppTest
 * @Date 2021/9/4 16:29
 * @Created by alison
 */
public class AppTest {

    @Test
    public void test(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Person.class);
    }
}
