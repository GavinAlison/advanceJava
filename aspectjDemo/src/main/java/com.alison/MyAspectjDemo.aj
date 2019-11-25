package com.alison;


import org.aspectj.lang.annotation.DeclarePrecedence;

/**
 * @Author alison
 * @Date 2019/11/17  14:05
 * @Version 1.0
 * @Description
 */
public aspect MyAspectjDemo {
    /**
     *  定义切点,日志记录切点
     */
//    pointcut  authCheck(): execution(public * com.alison.HelloWord.sayHello());
    pointcut  recordLog(): execution(public * com.alison.HelloWord.sayHello());
//    pointcut  getx(): execution(public * com.alison.HelloWord.sayHello());
    pointcut  sayHello2(): execution(public * com.alison.HelloWord.sayHello());
    pointcut  aroundAdvice(): execution(public * com.alison.HelloWord.sayHello());
    /**
     * 定义前置通知
     *
     * before(参数):连接点函数{
     *     函数体
     * }
     */
//    before():authCheck(){
//        System.out.println("sayHello方法执行前验证权限");
//    }
    /**
     * 定义后置通知 finally
     * after(参数):连接点函数{
     *     函数体
     * }
     */
    after():recordLog(){
        System.out.println("sayHello方法执行后记录日志");
    }
    /**
     * 定义后置通知带返回值
     * after(参数)returning(返回值类型):连接点函数{
     *     函数体
     * }
     */
//    after()returning(int x): getx(){
//        System.out.println("返回值为:" + x);
//    }
    /**
     * 异常通知
     * after(参数) throwing(返回值类型):连接点函数{
     *     函数体
     * }
     */
    after() throwing(Exception e):sayHello2(){
        System.out.println("抛出异常:" + e.toString());
    }
    /**
     * 环绕通知 可通过proceed()控制目标函数是否执行
     * Object around(参数):连接点函数{
     *     函数体
     *     Object result=proceed();//执行目标函数
     *     return result;
     * }
     */
    Object around():aroundAdvice(){
        System.out.println("sayAround 执行前执行");
        Object result = proceed();//执行目标函数
        System.out.println("sayAround 执行后执行");
        return result;
    }

}
