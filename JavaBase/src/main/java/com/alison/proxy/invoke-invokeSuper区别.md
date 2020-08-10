# invoke 与invokeSuper的区别


> 从[这篇文章](https://www.cnblogs.com/lvbinbin2yujie/p/10284316.html)中可以查看到
invoke与invokeSuper的区别，但是有一定的出入， 下面简单地总结一下。

1. cglib只是对类进行代理
2. cglib中的拦截器interceptor方法，如果使用method.invoke方法会造成循环引用
3. cglib 在调用方法的时候是调用代理对象的方法，代理对象会重写目标类的方法
4. 代理类不可以重写目标类的final修饰的方法
5. 为了避免调用自身而造成循环调用，可以将目标类对象传递到拦截器类中，拦截方法中用传递进来的对象
拦截器类实现MethodInterceptor,重写interceptor方法
6. spring aop 就是利用特性5 将对象传递到拦截器中实现无自我递归调用的
7. cglib利用enhandcer类进行增强，Enhancer既能够代理普通的class，也能够代理接口。
Enhancer创建一个被代理对象的子类并且拦截所有的方法调用（包括从Object中继承的toString和hashCode方法
8. Enhancer同样不能操作static或者final类



