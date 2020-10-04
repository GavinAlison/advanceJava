package com.alison.spring.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.HashMap;


/*
ClassMetadata
    AnnotationMetadata
        StandardAnnotationMetadata
        AnnotationMetadataReadingVisitor
    StandardAnnotationMetadata
        StandardAnnotationMetadata
    ClassMetadataReadingVisitor
        AnnotationMetadataReadingVisitor




 */

public class AnnotationApp {

    public static void main(String[] args) {
        StandardAnnotationMetadata metadata = new StandardAnnotationMetadata(MetaDemo.class, true);

        // 演示ClassMetadata的效果
        System.out.println("==============ClassMetadata==============");
        ClassMetadata classMetadata = metadata;
        // com.alison.spring.annotation.MetaDemo
        System.out.println(classMetadata.getClassName());
        // null  如果自己是内部类此处就有值了
        System.out.println(classMetadata.getEnclosingClassName());
        // com.alison.spring.annotation.MetaDemo$InnerClass 若木有内部类返回空数组[]
        System.out.println(StringUtils.arrayToCommaDelimitedString(classMetadata.getMemberClassNames()));
        // java.io.Serializable
        System.out.println(StringUtils.arrayToCommaDelimitedString(classMetadata.getInterfaceNames()));
        // true(只有Object这里是false)
        System.out.println(classMetadata.hasSuperClass());
        // java.util.HashMap
        System.out.println(classMetadata.getSuperClassName());

        // false（是否是注解类型的Class，这里显然是false）
        System.out.println(classMetadata.isAnnotation());
        // false
        System.out.println(classMetadata.isFinal());
        // true(top class或者static inner class，就是独立可new的)
        System.out.println(classMetadata.isIndependent());
        // 演示AnnotatedTypeMetadata的效果
        System.out.println("==============AnnotatedTypeMetadata==============");
        AnnotatedTypeMetadata annotatedTypeMetadata = metadata;
        // true（依赖的AnnotatedElementUtils.isAnnotated这个方法）
        System.out.println(annotatedTypeMetadata.isAnnotated(Service.class.getName()));
        // true
        System.out.println(annotatedTypeMetadata.isAnnotated(Component.class.getName()));
        //{value=serviceName}
        System.out.println(annotatedTypeMetadata.getAnnotationAttributes(Service.class.getName()));
        // {value=repositoryName}（@Repository的value值覆盖了@Service的）
        System.out.println(annotatedTypeMetadata.getAnnotationAttributes(Component.class.getName()));
        // {order=2147483647, annotation=interface java.lang.annotation.Annotation, proxyTargetClass=false, mode=PROXY}
        System.out.println(annotatedTypeMetadata.getAnnotationAttributes(EnableAsync.class.getName()));


        // 看看getAll的区别：value都是数组的形式
        // {value=[serviceName]}
        System.out.println(annotatedTypeMetadata.getAllAnnotationAttributes(Service.class.getName()));
        // {value=[, ]} --> 两个Component的value值都拿到了，只是都是空串而已
        System.out.println(annotatedTypeMetadata.getAllAnnotationAttributes(Component.class.getName()));
        //{order=[2147483647], annotation=[interface java.lang.annotation.Annotation], proxyTargetClass=[false], mode=[PROXY]}
        System.out.println(annotatedTypeMetadata.getAllAnnotationAttributes(EnableAsync.class.getName()));

        // 演示AnnotationMetadata子接口的效果（重要）
        System.out.println("==============AnnotationMetadata==============");
        AnnotationMetadata annotationMetadata = metadata;
        // 获取注解对应的属性
//        System.out.println(AnnotationConfigUtils.attributesFor(annotationMetadata, "org.springframework.scheduling.annotation.EnableAsync"));
        System.out.println(AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes("org.springframework.scheduling.annotation.EnableAsync", false)));
        // [org.springframework.stereotype.Repository, org.springframework.stereotype.Service, org.springframework.scheduling.annotation.EnableAsync]
        System.out.println(annotationMetadata.getAnnotationTypes());
        // [org.springframework.stereotype.Component, org.springframework.stereotype.Indexed]
        System.out.println(annotationMetadata.getMetaAnnotationTypes(Service.class.getName()));
        // []（meta就是获取注解上面的注解,会排除掉java.lang这些注解们）
        System.out.println(annotationMetadata.getMetaAnnotationTypes(Component.class.getName()));
        // true
        System.out.println(annotationMetadata.hasAnnotation(Service.class.getName()));
        // false（注意这里返回的是false）
        System.out.println("component==>" + annotationMetadata.hasAnnotation(Component.class.getName()));
        // false（注意这一组的结果和上面相反，因为它看的是meta）
        System.out.println(annotationMetadata.hasMetaAnnotation(Service.class.getName()));
        // true
        System.out.println(annotationMetadata.hasMetaAnnotation(Component.class.getName()));
        // true
        System.out.println(annotationMetadata.hasAnnotatedMethods(Autowired.class.getName()));
        annotationMetadata.getAnnotatedMethods(Autowired.class.getName()).forEach(methodMetadata -> {
            // class org.springframework.core.type.StandardMethodMetadata
            System.out.println(methodMetadata.getClass());
            // getName
            System.out.println(methodMetadata.getMethodName());
            // java.lang.String
            System.out.println(methodMetadata.getReturnTypeName());
        });
    }
}

@Repository("repositoryName")
@Service("serviceName")
@EnableAsync
class MetaDemo extends HashMap<String, String> implements Serializable {
    private static class InnerClass {
    }

    @Autowired
    private String getName() {
        return "demo";
    }
}
