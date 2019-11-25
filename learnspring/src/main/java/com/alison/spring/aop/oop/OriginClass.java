package com.alison.spring.aop.oop;

/**
 * @Author alison
 * @Date 2019/11/13  14:20
 * @Version 1.0
 * @Description
 */
public class OriginClass {

    class A {
        public void executeA() {
            //其他业务操作省略......
            recordLog();
        }

        public void recordLog() {
            //....记录日志并上报日志系统
        }
    }

    class B {
        public void executeB() {
            //其他业务操作省略......
            recordLog();
        }

        public void recordLog() {
            //....记录日志并上报日志系统
        }
    }

    class C {
        public void executeC(){
            //其他业务操作省略......
            recordLog();
        }

        public void recordLog(){
            //....记录日志并上报日志系统
        }
    }

}
