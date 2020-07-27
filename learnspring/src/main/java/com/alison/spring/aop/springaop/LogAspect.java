package com.alison.spring.aop.springaop;

import lombok.extern.java.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Author alison
 * @Date 2019/11/15  9:16
 * @Version 1.0
 * @Description 切面类
 */
@Log
@Component
@Aspect
public class LogAspect {

    /**
     * execution(* com.spring.spring.aop.springaop.*.save*(..))
     * * 第一个*表示任意返回值
     * * 第二个*表示com.spring.spring.aop.springaop包中所有类
     * * 第三个*表示以save开头的所有方法
     * * （..）表示任意参数
     */
    @Before("execution(* com.alison.spring.aop.springaop.*.save*(..)) || "
            + "execution(* com.alison.spring.aop.springaop.*.update*(..))"
            + "execution(* com.alison.spring.aop.springaop.*.delete*(..))")
    public void start(JoinPoint jp) {
        // 得到执行的对象
        System.out.println(jp.getTarget());
        // 得到执行的方法
        System.out.println(jp.getSignature().getName());
//        log.info("before method, log print");
    }

    /**
     * 程序结束之后执行
     *
     * @param jp
     */
    @After("execution(* com.alison.spring.aop.springaop.*.save*(..)) || "
            + "execution(* com.alison.spring.aop.springaop.*.update*(..))"
            + "execution(* com.alison.spring.aop.springaop.*.delete*(..))")
    public void logEnd(JoinPoint jp) {
        // 得到执行的对象
        System.out.println(jp.getTarget());
        // 得到执行的方法
        System.out.println(jp.getSignature().getName());
//        log.info("after method, log print");
    }

}
