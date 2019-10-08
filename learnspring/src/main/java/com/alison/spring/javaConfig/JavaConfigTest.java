package com.alison.spring.javaConfig;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author alison
 * @Date 2019/10/8  22:25
 * @Version 1.0
 * @Description
 */
public class JavaConfigTest {
    public static void main(String[] arg) {
        //case1
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        //case2
//        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//        ctx.register(AppConfig.class);
//        ctx.refresh();

        Entitlement ent = (Entitlement)ctx.getBean("entitlement");
        System.out.println(ent.getName());
        System.out.println(ent.getTime());

        Entitlement ent2 = (Entitlement)ctx.getBean("entitlement2");
        System.out.println(ent2.getName());
        System.out.println(ent2.getTime());

        ctx.close();
    }
}
