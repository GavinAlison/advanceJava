# Spring boot中@Conditional注解
> 下面讲解的是注解@Conditional的定义、使用、实现，该实现在Spring 容器中，
还有一种Annotaion 作用于编译时期，实现代码的自动编写，编译时期的注解实现原理是插件化注解处理器(pluggable annotation processing api),
像lombok中@Data注解。

下面以@Conditional 注解的定义，使用，实现三方面开始讲解。

### @Conditional定义
一句话： 多条件注入bean

spring-context.jar
org.springframework.context.annotation.Conditional

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Conditional {

	/**
	 * All {@link Condition Conditions} that must {@linkplain Condition#matches match}
	 * in order for the component to be registered.
	 */
	Class<? extends Condition>[] value();

}
```

### 使用
用在三处地方，1.type-level, 2.meta-annotaion 3. method
在Docement中有说明
```
<li>as a type-level annotation on any class directly or indirectly annotated with
 * {@code @Component}, including {@link Configuration @Configuration} classes</li>
 * <li>as a meta-annotation, for the purpose of composing custom stereotype
 * annotations</li>
 * <li>as a method-level annotation on any {@link Bean @Bean} method</li>
 * </ul>

```
1. type-level
```java
//@Configuration   没有必要在加configuration注解
@Conditional({OrderCondition.class}) // 会自动检测OrderCondition##matches的结果，true 注册容器, false 不进行注册
public class BeanConfig {
    
    //    @Conditional({OrderCondition.class, OrderFalse.class})
    @Bean(name = "order")
    public Order order() {
        return new Order(1, "Air", "Apple air");
    }

    @Bean("orderdetail")
    public Orderdetail orderDetail() {
        return new Orderdetail(1L, "购物者", 1L);
    }
}
```
2. meta-annotation
```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {
        /**
     * The classes that must be present. Since this annotation is parsed by loading class
     * bytecode, it is safe to specify classes here that may ultimately not be on the
     * classpath, only if this annotation is directly on the affected component and
     * <b>not</b> if this annotation is used as a composed, meta-annotation. In order to
     * use this annotation as a meta-annotation, only use the {@link #name} attribute.
     * @return the classes that must be present
     */
    Class<?>[] value() default {};

    /**
     * The classes names that must be present.
     * @return the class names that must be present.
     */
    String[] name() default {};

}
```
3. method-annotation
```java
@Configuration
public class BeanConfig {
    @Conditional({OrderCondition.class, OrderFalse.class})
    @Bean(name = "order")
    public Order order() {
        return new Order(1, "Air", "Apple air");
    }
}
```

**注意**

使用Conditional 注解， values是Condition接口的实现类，重写match()方法，根据返回的结果判断是否将方法返回对应的bean
注入到applicationcContext。
对应condition的match返回为true,注册到applicationContext中

在TYPE上， condition实现类的match的结果应用到所有的配置类下所有的bean

conditional对应的values值存在多个，都对应类的match方法，都返回true才注册，有一个不满足就不注册
```
Indicates that a component is only eligible for registration when all
 {@linkplain #value specified conditions} match.
```


### 实现原理
这个是运行时在spring 容器实现，实现类在`ConfigurationClassPostProcessor`, 实际实现类为`ConditionEvaluator`
方法shouldSkip， 最终还是会调用condition##matches方法

具体的实现:
```
查看SpringFramework的源码会发现加载使用这些注解的入口在ConfigurationClassPostProcessor中，
这个实现了BeanFactoryPostProcessor接口，前面介绍过，会嵌入到Spring的加载过程。
这个类主要是从ApplicationContext中取出Configuration注解的类并解析其中的注解，包括 @Conditional，@Import和 @Bean等。
解析 @Conditional 逻辑在ConfigurationClassParser类中，这里面用到了 ConditionEvaluator 这个类。

protected void processConfigurationClass(ConfigurationClass configClass) throws IOException {
    if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
        return;
    }
    ......
}

ConditionEvaluator中的shouldSkip方法则使用了 @Conditional中设置的Condition类。

public boolean shouldSkip(AnnotatedTypeMetadata metadata, ConfigurationPhase phase) {
    if (metadata == null || !metadata.isAnnotated(Conditional.class.getName())) {
        return false;
    }
    if (phase == null) {
        if (metadata instanceof AnnotationMetadata &&
                ConfigurationClassUtils.isConfigurationCandidate((AnnotationMetadata) metadata)) {
            return shouldSkip(metadata, ConfigurationPhase.PARSE_CONFIGURATION);
        }
        return shouldSkip(metadata, ConfigurationPhase.REGISTER_BEAN);
    }
    List<Condition> conditions = new ArrayList<Condition>();
    for (String[] conditionClasses : getConditionClasses(metadata)) {
        for (String conditionClass : conditionClasses) {
            Condition condition = getCondition(conditionClass, this.context.getClassLoader());
            conditions.add(condition);
        }
    }
    AnnotationAwareOrderComparator.sort(conditions);
    for (Condition condition : conditions) {
        ConfigurationPhase requiredPhase = null;
        if (condition instanceof ConfigurationCondition) {
            requiredPhase = ((ConfigurationCondition) condition).getConfigurationPhase();
        }
        if (requiredPhase == null || requiredPhase == phase) {
            if (!condition.matches(this.context, metadata)) {
                return true;
            }
        }
    }
    return false;
}
private List<String[]> getConditionClasses(AnnotatedTypeMetadata metadata) {
        MultiValueMap<String, Object> attributes = metadata.getAllAnnotationAttributes(Conditional.class.getName(), true);
        Object values = (attributes != null ? attributes.get("value") : null);
        return (List<String[]>) (values != null ? values : Collections.emptyList());
}
```
### spring boot 重新定义的Condition
@ConditionalOnBean（仅仅在当前上下文中存在某个对象时，才会实例化一个Bean）
@ConditionalOnClass（某个class位于类路径上，才会实例化一个Bean）
@ConditionalOnExpression（当表达式为true的时候，才会实例化一个Bean）
@ConditionalOnMissingBean（仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean）
@ConditionalOnMissingClass（某个class类路径上不存在的时候，才会实例化一个Bean）
@ConditionalOnNotWebApplication（不是web应用）


### 自定义Conditional
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClazzCondition.class)
public @interface ConditionOnClazz {
    Class<?>[] value();
}

// 注解实现
public class OnClazzCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        context==>ConditionEvaluator$ConditionContextImpl
        System.out.println(context);
//        metadata ==> StandardAnnotationMetadata
        System.out.println(metadata);
//        metadata.getAnnotationAttributes("com.alison.app.condition.ConditionOnClazz")
//        {value=[class com.alison.app.condition.pojo.Order]}
        Map<String, Object> conditionMap = metadata.getAnnotationAttributes(ConditionOnClazz.class.getName());
        return ClassUtils.isPresent(((Class[]) conditionMap.get("value"))[0].getName(), OnClazzCondition.class.getClassLoader());
    }
}

@ConditionOnClazz(Order.class)
public class Config{...}
```


>引用
1. https://blog.csdn.net/xcy1193068639/article/details/81491071
2. https://www.cnblogs.com/hlkawa/p/11088129.html
3. [深入SpringBoot:自定义Conditional](https://www.jianshu.com/p/1d0fb7cd8a26)
4. https://blog.csdn.net/xiaohanzuofengzhou/article/details/102722892
5. https://www.jianshu.com/p/e93904733ef8
