package com.alison.spring.aop.oop;

/**
 * @Author alison
 * @Date 2019/11/13  14:25
 * @Version 1.0
 * @Description
 */
public class OopClass {
    //A类
    class A {
        public void executeA() {
            //其他业务操作省略...... args 参数，一般会传递类名，方法名称 或信息（这样的信息一般不轻易改动）
//            Report.recordLog(args ...);
        }
    }

    //B类
    class B {
        public void executeB() {
            //其他业务操作省略......
//            Report.recordLog(args ...);
        }
    }

    //C类
    class C {
        public void executeC() {
            //其他业务操作省略......
//            Report.recordLog(args ...);
        }
    }

    //record
    static class Report {
//        public static void recordLog(args...) {
//            ....记录日志并上报日志系统
//        }
    }
}
