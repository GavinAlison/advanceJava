# spring aop 
实现通知的两种方法

-   通过接口
-   通过注解

## 通过接口

通知
-   前置通知
-   后置通知
-   异常通知
-   环绕通知
-   最终通知

AOP, 面向切面编程，简单的说，就是拦截器，跟拦截器的作用相似，
不用在源码处写入硬编码，直接拦截对应的方法，加入一下处理。

### 接口实现的通知
-   前置通知    
继承MethodBeforeAdvice接口，重写before方法
-   后置通知    
继承AfterReturningAdvice接口，重写afterReturning方法
-   异常通知    
继承 ThrowsAdvice接口，无重写方法
-   环绕通知    
继承MethodInterceptor接口，重写invoke方法
-   最红通知    

**工具**
jar包
aopaliance.jar
aspectjweaver.jar




