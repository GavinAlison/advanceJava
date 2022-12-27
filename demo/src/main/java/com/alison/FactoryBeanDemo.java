package com.alison;

/**
 * @Classname FactoryBeanDemo
 * @Date 2021/9/4 18:06
 * @Created by alison
 */
public class FactoryBeanDemo {
    // factory-bean和Factory-method的作用
    // bean 创建方式
    // 1. xml bean
    // 2. @Bean 等注解
    // 3. FactoryBean 工厂类创建
    // 4. Factory-method属性创建对象, 引用的是静态工厂方法

    //    <bean id="car4" factory-bean="carFactory" factory-method="getCar" />
    // factory-bean 实例对象，  factory-method 实例方法
    //    <bean id="bmwCar" class="com.home.factoryMethod.CarStaticFactory" factory-method="getCar"/>
    // class 静态工厂类,   factory-method  静态工厂方法

}
