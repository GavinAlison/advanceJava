##问题一
1. 出现.classfile.ClassFormatException: File: 'java/lang/CharSequence.class': Invalid byte tag in constant pool: 15
查看原因，编译的插件支持1.4,不支持1.8，修改，让其支持1.8， 在pom.xml中修改

2. 解决对同一个切点进行多次增强， 他们之间的执行顺序是什么？ 
这块还想无法确定顺序
can't determine precedence between two or more pieces of advice 

找到stackoverflow中同样的问题，[AspectJ: execution order (precedence) for multiple advice within one aspect](https://stackoverflow.com/questions/11850160/aspectj-execution-order-precedence-for-multiple-advice-within-one-aspect)
```java
@Aspect
public class SecurityInterceptor {

    @Pointcut("within(@org.springframework.stereotype.Controller *)")
    public void beanAnnotatedWithController() {}

    @Pointcut("execution(public * *(..)) && args(*,httpReq)")
    public void publicMethods(HttpServletRequest httpReq) {}

    @Pointcut("beanAnnotatedWithController() && publicMethods(httpReq)")
    public void controllerMethods(HttpServletRequest httpReq) {}

    @Pointcut("execution(public * *(..)) && args(httpReq)")
    public void publicMethodsRequestOnly(HttpServletRequest httpReq) {}

    @Pointcut("beanAnnotatedWithController() && publicMethodsRequestOnly(httpReq)")
    public void controllerMethodsOneArg(HttpServletRequest httpReq) {}


    @Around(value = "controllerMethods(httpReq)")
    public Object populateSecurityContext(final ProceedingJoinPoint joinPoint, HttpServletRequest httpReq) throws Throwable {
        return popSecContext(joinPoint, httpReq);
    }

    @Around(value = "controllerMethodsOneArg(httpReq)")
    public Object populateSecurityContextOneArg(final ProceedingJoinPoint joinPoint, HttpServletRequest httpReq) throws Throwable {
        return popSecContext(joinPoint, httpReq);
    }

}
```

解答一：
```
Please read paragraph "Advice precedence" in the language semantics section 
of the AspectJ documentation.
Precedence of aspects can be declared explicitly, precedence of advice 
within a single aspect is determined by rules described in the document 
and cannot be changed, AFAIK. So @DeclarePrecedence will not help you 
in this case, only changing the order of advice within the aspect file.
```
language semantics--->https://www.eclipse.org/aspectj/doc/next/progguide/semantics-advice.html

解答二：
```
When two pieces of advice defined in the same aspect both need to run 
at the same join point, the ordering is undefined (since there is no way 
to retrieve the declaration order via reflection for javac-compiled classes). 
Consider collapsing such advice methods into one advice method per joinpoint 
in each aspect class, or refactor the pieces of advice into separate aspect 
classes - which can be ordered at the aspect level.

from Spring AOP documentation here (section 6.2.4.7. Advice ordering) 
https://docs.spring.io/spring/docs/2.0.x/reference/aop.html

If it helps in case you came looking for this here.
```
When multiple advice needs to execute at the same join point 
(executing method) the ordering rules are as described in Section 6.2.4.7,
 “Advice ordering”. The precedence between aspects is determined by either 
 adding the Order annotation to the bean backing the aspect 
or by having the bean implement the Ordered interface.

最终解决：
查看[官方文档](https://www.eclipse.org/aspectj/doc/next/progguide/semantics-advice.html)，
需要将这些拆分开来。





