# Spring boot中@Conditional注解
> 下面讲解的是注解@Conditional的定义、使用、实现，该实现在Spring 容器中，
还有一种Annotaion 作用于编译时期，实现代码的自动编写，编译时期的注解实现原理是插件化注解处理器(pluggable annotation processing api),
像lombok中@Data注解。

下面以@Conditional 注解的定义，使用，实现三方面开始讲解。

### @Conditional定义
一句话： 多条件注入bean

spring-context.jar
org.springframework.context.annotation.Conditional

```
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
该注解使用在TYPE， 或者METHOD方法上。
在METHOD上，可以使用Conditional 注解， values是Condition接口的实现类，重写match()方法，根据返回的结果判断是否将方法返回对应的bean
注入到applicationcContext。

对应condition的match返回为true,注册到applicationContext中

在TYPE上， condition实现类的match的结果应用到所有的配置类下所有的bean

conditional对应的values值存在多个，都对应类的match方法，都返回true才注册，有一个不满足就不注册
```
Indicates that a component is only eligible for registration when all
 {@linkplain #value specified conditions} match.
```


### 实现原理
这个是运行时在spring 容器实现，实现类在`ConfigurationClassPostProcessor`, 实际实现类为`ConfigurationClassParser`

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




### 自定义Conditional





>引用
1. https://blog.csdn.net/xcy1193068639/article/details/81491071
2. https://www.cnblogs.com/hlkawa/p/11088129.html
3. [深入SpringBoot:自定义Conditional](https://www.jianshu.com/p/1d0fb7cd8a26)
4. https://blog.csdn.net/xiaohanzuofengzhou/article/details/102722892
5. https://www.jianshu.com/p/e93904733ef8
