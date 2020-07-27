package com.alison.spring.aop.oop;

/**
 * @Author alison
 * @Date 2019/11/13  14:31
 * @Version 1.0
 * @Description
 */
public class Oopclass2 {
    //通用父类
    public class Dparent {
        public void commond(){
            //通用代码
        }
    }
    //A 继承 Dparent
    public class A extends Dparent {
        public void executeA(){
            //其他业务操作省略......
            commond();
        }
    }
    //B 继承 Dparent
    public class B extends Dparent{
        public void executeB(){
            //其他业务操作省略......
            commond();
        }
    }
    //C 继承 Dparent
    public class C extends Dparent{
        public void executeC(){
            //其他业务操作省略......
            commond();
        }
    }
}
