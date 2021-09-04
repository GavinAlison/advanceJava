package com.alison;

import lombok.Data;

/**
 * @Classname BeanA
 * @Date 2021/9/4 16:25
 * @Created by alison
 */
@Data
public class Person  {
    private String name;
    private Integer age;
    public Person(){
        System.out.println("person constructor...");
    }
}
